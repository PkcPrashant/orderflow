package com.orderflow.modules.orders.controller;

import com.orderflow.modules.orders.dto.request.OrderRequest;
import com.orderflow.modules.orders.dto.response.OrderResponse;
import com.orderflow.modules.orders.entity.Order;
import com.orderflow.modules.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.saveOrder(orderRequest);
        OrderResponse orderResponse = new OrderResponse(order.getOrderNo(), order.getCreatedTime());
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

}
