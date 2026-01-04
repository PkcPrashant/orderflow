package com.orderflow.modules.auth.service;

import com.orderflow.common.exception.DuplicateResourceException;
import com.orderflow.common.exception.ResourceNotFoundException;
import com.orderflow.modules.auth.dto.request.CreateUserRequest;
import com.orderflow.modules.auth.dto.request.LoginUserRequest;
import com.orderflow.modules.auth.entity.User;
import com.orderflow.modules.auth.repo.UserRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public User createUser(CreateUserRequest createUserRequest) {
        String email = createUserRequest.email();
        if (userRepo.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already exists");
        }
        String hashed = passwordEncoder.encode(createUserRequest.password());
        User user = new User(email, hashed, createUserRequest.role());
        return userRepo.save(user);
    }

    public User loginUser(LoginUserRequest loginUserRequest) {
        User user = userRepo.findByEmail(loginUserRequest.email()).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(loginUserRequest.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return user;
    }
}
