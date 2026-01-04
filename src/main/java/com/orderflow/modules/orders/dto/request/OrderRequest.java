package com.orderflow.modules.orders.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrderRequest(
        @NotBlank(message = "Order number is required")
        String orderNo
){}
