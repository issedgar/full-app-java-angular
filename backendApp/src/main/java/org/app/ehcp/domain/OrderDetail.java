package org.app.ehcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;
    private BigDecimal total;

    @OneToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "article_id")
    @JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler" })
    @NotNull
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler" })
    private Order order;
}
