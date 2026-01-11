package com.orderflow.modules.auth.controller;

import com.orderflow.modules.auth.dto.request.LoginRequest;
import com.orderflow.modules.auth.dto.request.RegisterRequest;
import com.orderflow.modules.auth.dto.response.AuthResponse;
import com.orderflow.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        String token = authService.createUser(registerRequest);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.loginUser(loginRequest);
        return new AuthResponse(token);
    }
}
