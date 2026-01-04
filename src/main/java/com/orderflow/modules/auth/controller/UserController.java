package com.orderflow.modules.auth.controller;

import com.orderflow.modules.auth.dto.request.CreateUserRequest;
import com.orderflow.modules.auth.dto.request.LoginUserRequest;
import com.orderflow.modules.auth.dto.response.UserResponse;
import com.orderflow.modules.auth.entity.User;
import com.orderflow.modules.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return new UserResponse(user.getEmail(), user.getRole());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        User user = userService.loginUser(loginUserRequest);
        return new UserResponse(user.getEmail(), user.getRole());
    }
}
