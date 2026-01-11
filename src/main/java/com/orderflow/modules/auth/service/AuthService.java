package com.orderflow.modules.auth.service;

import com.orderflow.common.exception.DuplicateResourceException;
import com.orderflow.modules.auth.dto.request.LoginRequest;
import com.orderflow.modules.auth.dto.request.RegisterRequest;
import com.orderflow.modules.auth.entity.User;
import com.orderflow.modules.auth.repo.UserRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepo userRepo, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    @Transactional
    public String createUser(RegisterRequest registerRequest) {
        String email = registerRequest.email();
        if (userRepo.existsByEmail(email)) {
                throw new DuplicateResourceException("Email already exists");
        }
        String hashed = passwordEncoder.encode(registerRequest.password());
        User user = new User(email, hashed);
        userRepo.save(user);
        return jwtService.generateToken(user);
    }

    @Transactional
    public String loginUser(LoginRequest registerRequest) {
        User user = userRepo.findByEmail(registerRequest.email()).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(registerRequest.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }
}
