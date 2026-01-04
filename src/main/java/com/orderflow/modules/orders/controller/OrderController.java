package com.orderflow.modules.orders.controller;

import com.orderflow.modules.orders.dto.request.OrderRequest;
import com.orderflow.modules.orders.dto.response.OrderResponse;
import com.orderflow.modules.orders.entity.Order;
import com.orderflow.modules.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return new OrderResponse(order.getOrderNo(), order.getCreatedTime());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrderById(@Valid @PathVariable Integer id) {
        Order order = orderService.findOrderById(id);
        return new OrderResponse(order.getOrderNo(), order.getCreatedTime());
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrderByOrderNo(@Valid @RequestParam String orderNo) {
        Order order = orderService.findByOrderNo(orderNo);
        return new OrderResponse(order.getOrderNo(), order.getCreatedTime());
    }

}
