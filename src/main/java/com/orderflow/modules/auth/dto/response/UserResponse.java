package com.orderflow.modules.auth.dto.response;

public record UserResponse(
    String email,
    String role
) { }
