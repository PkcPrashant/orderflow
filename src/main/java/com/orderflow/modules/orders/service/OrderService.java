package com.orderflow.modules.orders.service;

import com.orderflow.modules.orders.dto.request.OrderRequest;
import com.orderflow.modules.orders.entity.Order;
import com.orderflow.modules.orders.exception.DuplicateOrderNoException;
import com.orderflow.modules.orders.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        String orderNo = orderRequest.orderNo();
        if (orderRepo.existsByOrderNo(orderNo)) {
            throw new DuplicateOrderNoException("Order no already exists");
        } else {
            return orderRepo.save(new Order(orderNo));
        }
    }
}
