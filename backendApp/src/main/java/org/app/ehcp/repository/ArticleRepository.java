package org.app.ehcp.repository;

import org.app.ehcp.domain.Article;
import org.app.ehcp.projection.ArticlePageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByCode(String code);
    Optional<Article> findByCodeAndStoreId(String code, Long storeId);
    @Query("SELECT a.id AS id, a.code AS code, a.description AS description, a.price AS price, a.image AS image, a.stock AS stock, a.store.id AS storeId FROM Article a")
    Page<ArticlePageProjection> findAllProjectedBy(Pageable pageable);
    @Query("SELECT a.id AS id, a.code AS code, a.description AS description, a.price AS price, a.image AS image, a.stock AS stock, a.store.id AS storeId FROM Article a WHERE a.store.id = :storeId")
    List<ArticlePageProjection> findAllProjectedByStoreId(@Param("storeId") Long storeId);
}
