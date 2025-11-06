package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.dto.auth.AuthRequest;
import com.ktiservice.patrimoine.dto.auth.AuthResponse;
import com.ktiservice.patrimoine.dto.auth.RegisterRequest;
import com.ktiservice.patrimoine.services.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }
}
