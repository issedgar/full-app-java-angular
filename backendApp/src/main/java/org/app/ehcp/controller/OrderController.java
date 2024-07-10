package org.app.ehcp.controller;

import org.app.ehcp.domain.dto.OrderCreateDTO;
import org.app.ehcp.domain.dto.response.OrderResponseDTO;
import org.app.ehcp.service.interfaces.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public OrderResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/all-by-store/{storeId}")
    public ResponseEntity<List<OrderResponseDTO>> getByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(service.findAllByStoreId(storeId));
    }

    @PostMapping
    public OrderResponseDTO create(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return service.create(orderCreateDTO);
    }
}
