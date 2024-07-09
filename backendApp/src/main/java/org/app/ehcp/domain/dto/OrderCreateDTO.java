package org.app.ehcp.domain.dto;

import lombok.Data;
import org.app.ehcp.domain.Client;
import org.app.ehcp.domain.Order;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCreateDTO {
    @NotNull
    private String orderNum;

    @NotNull
    private Long clientId;

    @NotNull
    private List<OrderDetailCreateDTO> orderDetailList;

    public static Order convertToOrder(OrderCreateDTO orderCreateDTO) {
        if(orderCreateDTO == null) {
            return null;
        }
        Order order = new Order();

        BeanUtils.copyProperties(orderCreateDTO, order);
        Client client = new Client();
        client.setId(orderCreateDTO.getClientId());
        order.setClient(client);
        return order;
    }
}
