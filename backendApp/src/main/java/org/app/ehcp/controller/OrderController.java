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

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getByPage() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public OrderResponseDTO create(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return service.create(orderCreateDTO);
    }
}
