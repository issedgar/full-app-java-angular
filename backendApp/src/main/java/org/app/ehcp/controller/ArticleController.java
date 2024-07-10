package org.app.ehcp.controller;

import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.dto.ArticleCreateDTO;
import org.app.ehcp.domain.dto.ArticleUpdateDTO;
import org.app.ehcp.projection.ArticlePageProjection;
import org.app.ehcp.service.interfaces.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Article get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ArticlePageProjection>> getByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.findByPage(PageRequest.of(page, size, Sort.by("id").descending())));
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<ArticlePageProjection>> getByStore(@PathVariable Long storeId ) {
        return ResponseEntity.ok(service.findByStore(storeId));
    }

    @PostMapping
    public Article create(@Valid @RequestBody ArticleCreateDTO articleCreateDTO) {
        return service.create(articleCreateDTO);
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable Long id, @Valid @RequestBody ArticleUpdateDTO articleUpdateDTO) {
        return service.update(id, articleUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
