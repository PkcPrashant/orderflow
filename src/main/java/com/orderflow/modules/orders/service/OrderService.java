package com.orderflow.modules.orders.service;

import com.orderflow.modules.orders.dto.request.OrderRequest;
import com.orderflow.modules.orders.entity.Order;
import com.orderflow.common.exception.DuplicateResourceException;
import com.orderflow.common.exception.ResourceNotFoundException;
import com.orderflow.modules.orders.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new DuplicateResourceException("Order no already exists");
        } else {
            return orderRepo.save(new Order(orderNo));
        }
    }

    public Order findOrderById(Integer id) {
        return orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found for id: " + id)
                );
    }

    public Order findByOrderNo(String orderNo) {
        return orderRepo.findByOrderNo(orderNo)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found for orderNo: " + orderNo)
                );

    }
}
