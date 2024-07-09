package org.app.ehcp.service.interfaces;

import org.app.ehcp.domain.dto.OrderCreateDTO;
import org.app.ehcp.domain.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO get(Long id);
    OrderResponseDTO create(OrderCreateDTO orderCreateDTO);
    List<OrderResponseDTO> findAll();
}
