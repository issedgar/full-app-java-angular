package org.app.ehcp.service.interfaces;

import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.dto.ArticleCreateDTO;
import org.app.ehcp.domain.dto.ArticleUpdateDTO;
import org.app.ehcp.projection.ArticlePageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Article get(Long id);
    Article create(ArticleCreateDTO articleCreateDTO);
    Article update(Long id, ArticleUpdateDTO articleUpdateDTO);
    void delete(Long id);
    Page<ArticlePageProjection> findByPage(Pageable pageable);
}
