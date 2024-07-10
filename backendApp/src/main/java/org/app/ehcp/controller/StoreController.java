package org.app.ehcp.controller;

import org.app.ehcp.domain.Store;
import org.app.ehcp.domain.dto.StoreDTO;
import org.app.ehcp.domain.dto.StoreUpdateDTO;
import org.app.ehcp.service.interfaces.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {
    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Store get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Store>> getByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findByPage(PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Store>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public Store create(@Valid @RequestBody StoreDTO storeDTO) {
        return service.create(storeDTO);
    }

    @PutMapping("/{id}")
    public Store update(@PathVariable Long id, @Valid @RequestBody StoreUpdateDTO storeUpdateDTO) {
        return service.update(id, storeUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
