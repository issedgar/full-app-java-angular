package org.app.ehcp.service;

import lombok.extern.slf4j.Slf4j;
import org.app.ehcp.domain.*;
import org.app.ehcp.domain.dto.OrderCreateDTO;
import org.app.ehcp.domain.dto.OrderDetailCreateDTO;
import org.app.ehcp.domain.dto.response.ClientResponseDTO;
import org.app.ehcp.domain.dto.response.OrderDetailResponseDTO;
import org.app.ehcp.domain.dto.response.OrderResponseDTO;
import org.app.ehcp.repository.ArticleRepository;
import org.app.ehcp.repository.ClientRepository;
import org.app.ehcp.repository.OrderDetailRepository;
import org.app.ehcp.repository.OrderRepository;
import org.app.ehcp.service.interfaces.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ClientRepository clientRepository, ArticleRepository articleRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO create(OrderCreateDTO orderCreateDTO) {

        Optional<Order> orderOptional = orderRepository.findByOrderNum(orderCreateDTO.getOrderNum());
        if(orderOptional.isPresent()) {
            log.error("Order exists whit num");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order exists whit num");
        }

        Optional<Client> clientOptional = clientRepository.findById(orderCreateDTO.getClientId());
        if(clientOptional.isEmpty()) {
            log.error("Client exists whit id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client exists whit id");
        }

        try {

            Order order = OrderCreateDTO.convertToOrder(orderCreateDTO);
            Order orderSave = orderRepository.save(order);

            List<OrderDetail> orderDetailList = orderCreateDTO.getOrderDetailList()
                    .stream()
                    .map(od -> {
                        Optional<Article> articleOptional = articleRepository.findById(od.getArticleId());
                        if(articleOptional.isEmpty()) {
                            log.error("Article not exists whit id");
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article not exists whit id");
                        }
                        return OrderDetailCreateDTO.convertToOrderDetail(od, orderSave, articleOptional.get());
                    }).toList();

            List<OrderDetail> orderDetailListSave =orderDetailRepository.saveAll(orderDetailList);

            OrderResponseDTO orderResponseDTO = OrderResponseDTO.convertToOrderResponseDTO(orderSave);
            orderResponseDTO.setOrdersDetail(
                    orderDetailListSave.stream().map(OrderDetailResponseDTO::convertToOrderDetailResponseDTO)
                            .toList()
            );
            orderResponseDTO.setClient(ClientResponseDTO.convertToClientResponseDTO(clientOptional.get()) );

            return orderResponseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error create");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error create");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO get(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()) {
            log.error("Order not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
        }

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.convertToOrderResponseDTO(orderOptional.get());

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderOptional.get().getId());

        orderResponseDTO.setOrdersDetail(
                orderDetailList.stream().map(OrderDetailResponseDTO::convertToOrderDetailResponseDTO)
                        .collect(Collectors.toList())
        );
        return orderResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO>  findAll() {
        List<Order> orderList = orderRepository.findAll();

        return orderList.stream().map( order -> {
            OrderResponseDTO orderResponseDTO = OrderResponseDTO.convertToOrderResponseDTO(order);
            List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());
            orderResponseDTO.setOrdersDetail(
                    orderDetailList.stream().map(OrderDetailResponseDTO::convertToOrderDetailResponseDTO)
                            .collect(Collectors.toList())
            );

            return orderResponseDTO;
        }).collect(Collectors.toList());
    }
}
