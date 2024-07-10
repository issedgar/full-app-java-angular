package org.app.ehcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String orderNum;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler" })
    private Client client;

    private Long storeId;

}
