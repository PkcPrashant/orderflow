package com.orderflow.modules.orders.service;

import com.orderflow.modules.orders.dto.request.OrderRequest;
import com.orderflow.modules.orders.entity.Order;
import com.orderflow.modules.orders.repo.OrderRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order saveOrder(@Valid OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNo(orderRequest.orderNo());
        order.setCreatedTime(LocalDateTime.now());
        return orderRepo.save(order);
    }
}
