package org.app.ehcp.domain.dto;

import lombok.Data;
import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.Order;
import org.app.ehcp.domain.OrderDetail;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderDetailCreateDTO {
    private Integer count;
    private BigDecimal total;
    @NotNull
    private Long articleId;

    public static OrderDetail convertToOrderDetail(OrderDetailCreateDTO orderDetailCreateDTO, Order order, Article article) {
        if(orderDetailCreateDTO == null) {
            return null;
        }
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderDetailCreateDTO, orderDetail);

        orderDetail.setArticle(article);
        orderDetail.setOrder(order);
        return orderDetail;
    }
}
