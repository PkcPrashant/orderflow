package com.orderflow.modules.orders.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 40)
    private String orderNo;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    protected Order() {}

    public Order(String orderNo) {
        this.orderNo = orderNo;
        this.createdTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
