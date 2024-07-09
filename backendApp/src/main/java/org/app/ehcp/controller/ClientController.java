package org.app.ehcp.controller;

import org.app.ehcp.domain.dto.ClientCreateDTO;
import org.app.ehcp.domain.dto.ClientUpdateDTO;
import org.app.ehcp.domain.dto.response.ClientResponseDTO;
import org.app.ehcp.service.interfaces.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ClientResponseDTO get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ClientResponseDTO>> getByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(clientService.findByPage(PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @PostMapping
    public ClientResponseDTO create(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        return clientService.create(clientCreateDTO);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO update(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
        return clientService.update(id, clientUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

}
