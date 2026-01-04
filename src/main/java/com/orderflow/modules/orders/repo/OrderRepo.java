package com.orderflow.modules.orders.repo;

import com.orderflow.modules.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    boolean existsByOrderNo(String orderNo);

    Optional<Order> findByOrderNo(String orderNo);
}
