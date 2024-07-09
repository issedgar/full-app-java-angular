package org.app.ehcp.domain.dto.response;

import lombok.Data;
import org.app.ehcp.domain.Article;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
public class ArticleResponseDTO {
    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private String image;
    private Integer stock;
    private Long storeId;

    public static ArticleResponseDTO convertToArticleResponseDTO(Article article) {
        if(article == null) {
            return null;
        }
        ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
        BeanUtils.copyProperties(article, articleResponseDTO);
        return articleResponseDTO;
    }

}
