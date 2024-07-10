package org.app.ehcp.domain.dto.response;

import lombok.Data;
import org.app.ehcp.domain.Order;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private String orderNum;
    private Long storeId;

    private ClientResponseDTO client;

    private List<OrderDetailResponseDTO> ordersDetail;

    public static OrderResponseDTO convertToOrderResponseDTO(Order order) {
        if(order == null) {
            return null;
        }
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        BeanUtils.copyProperties(order, orderResponseDTO);
        orderResponseDTO.setClient(ClientResponseDTO.convertToClientResponseDTO(order.getClient()));
        return orderResponseDTO;
    }

}
