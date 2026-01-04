package com.orderflow.modules.orders.dto.response;

import java.time.LocalDateTime;

public record OrderResponse(
        String orderNo,
        LocalDateTime createdTime
){}
