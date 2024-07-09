package org.app.ehcp.domain.dto;

import lombok.Data;
import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.Store;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ArticleCreateDTO {
    @NotNull
    private String code;
    private String description;
    @NotNull
    private BigDecimal price;
    private String image;
    @NotNull
    private Integer stock;
    @NotNull
    private Long storeId;

    public static Article convertToArticle(ArticleCreateDTO articleCreateDTO) {
        if(articleCreateDTO == null) {
            return null;
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleCreateDTO, article);

        Store store = new Store();
        store.setId(articleCreateDTO.getStoreId());
        article.setStore(store);
        return article;
    }
}
