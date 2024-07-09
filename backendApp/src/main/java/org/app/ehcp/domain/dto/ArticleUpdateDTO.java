package org.app.ehcp.domain.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ArticleUpdateDTO {
    private String code;
    private String description;
    private BigDecimal price;
    private String image;
    private Integer stock;

}
