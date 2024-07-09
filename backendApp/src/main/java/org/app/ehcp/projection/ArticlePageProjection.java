package org.app.ehcp.projection;

import java.math.BigDecimal;

public interface ArticlePageProjection {
    Long getId();
    String getCode();
    String getDescription();
    BigDecimal getPrice();
    String getImage();
    Integer getStock();
    Long getStoreId();
}
