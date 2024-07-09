package org.app.ehcp.repository;

import org.app.ehcp.domain.Article;
import org.app.ehcp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNum(String orderNum);
}
