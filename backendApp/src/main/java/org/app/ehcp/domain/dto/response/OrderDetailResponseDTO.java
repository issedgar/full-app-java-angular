package org.app.ehcp.domain.dto.response;

import lombok.Data;
import org.app.ehcp.domain.OrderDetail;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
public class OrderDetailResponseDTO {
    private Long id;
    private Integer count;
    private BigDecimal total;

    private ArticleResponseDTO article;

    public static OrderDetailResponseDTO convertToOrderDetailResponseDTO(OrderDetail orderDetail) {
        if(orderDetail == null) {
            return null;
        }
        OrderDetailResponseDTO orderDetailResponseDTO = new OrderDetailResponseDTO();
        BeanUtils.copyProperties(orderDetail, orderDetailResponseDTO);
        orderDetailResponseDTO.setArticle(
                ArticleResponseDTO.convertToArticleResponseDTO(orderDetail.getArticle())
                );

        return orderDetailResponseDTO;
    }

}
