package org.app.ehcp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.Store;
import org.app.ehcp.domain.dto.ArticleCreateDTO;
import org.app.ehcp.domain.dto.ArticleUpdateDTO;
import org.app.ehcp.projection.ArticlePageProjection;
import org.app.ehcp.repository.ArticleRepository;
import org.app.ehcp.repository.StoreRepository;
import org.app.ehcp.service.interfaces.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;
    private final StoreRepository storeRepository;

    public ArticleServiceImpl(ArticleRepository repository, StoreRepository storeRepository) {
        this.repository = repository;
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional
    public Article create(ArticleCreateDTO articleCreateDTO) {
        Optional<Article> articleOptional = repository.findByCode(articleCreateDTO.getCode());
        if(articleOptional.isPresent()) {
            log.error("Article exists whit code");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article exists whit code");
        }

        Optional<Store> storeOptional = storeRepository.findById(articleCreateDTO.getStoreId());
        if(storeOptional.isEmpty()) {
            log.error("Store exists whit id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store exists whit id");
        }

        Store store = storeOptional.get();
        try {
            Article article = ArticleCreateDTO.convertToArticle(articleCreateDTO);
            Article articleSave = repository.save(article);
            articleSave.setStore(store);
            return articleSave;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error create");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error create");
        }
    }

    @Override
    @Transactional
    public Article update(Long id, ArticleUpdateDTO articleUpdateDTO) {
        Optional<Article> articleOptional = repository.findById(id);
        if(articleOptional.isEmpty()) {
            log.error("Article not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article not found");
        }

        Article article = articleOptional.get();

        if(Strings.isNotEmpty(articleUpdateDTO.getCode())) {
            article.setCode(articleUpdateDTO.getCode());
        }
        if(Strings.isNotEmpty(articleUpdateDTO.getDescription())) {
            article.setDescription(articleUpdateDTO.getDescription());
        }
        if(Strings.isNotEmpty(articleUpdateDTO.getImage())) {
            article.setImage(articleUpdateDTO.getImage());
        }
        if(articleUpdateDTO.getStock() != null) {
            article.setStock(articleUpdateDTO.getStock());
        }
        if(articleUpdateDTO.getPrice() != null) {
            article.setPrice(articleUpdateDTO.getPrice());
        }

        try {
            Article articleSave = repository.save(article);
            Optional<Store> store = storeRepository.findById(article.getStore().getId());
            store.ifPresent(articleSave::setStore);
            return articleSave;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error update");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error update");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Article> articleOptional = repository.findById(id);
        if(articleOptional.isEmpty()) {
            log.error("Article not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article not found");
        }
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error delete");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error delete");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Article get(Long id) {
        Optional<Article> articleOptional = repository.findById(id);
        if(articleOptional.isEmpty()) {
            log.error("Article not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article not found");
        }
        return articleOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticlePageProjection> findByPage(Pageable pageable) {
        return repository.findAllProjectedBy(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticlePageProjection> findByStore(Long storeId) {
        return repository.findAllProjectedByStoreId(storeId);
    }
}
