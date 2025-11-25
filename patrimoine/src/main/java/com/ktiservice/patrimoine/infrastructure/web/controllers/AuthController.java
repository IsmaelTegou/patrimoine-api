package com.ktiservice.patrimoine.infrastructure.web.controllers;

import com.ktiservice.patrimoine.application.services.AuthenticationApplicationService;
import com.ktiservice.patrimoine.infrastructure.web.dto.auth.*;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Authentication and User Registration.
 * Provides login, registration, and token refresh endpoints.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class AuthController {

    private final AuthenticationApplicationService authService;

    public AuthController(AuthenticationApplicationService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user.
     */
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account (email verification required)")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterRequest request) {
        log.info("User registration attempt for email: {}", request.getEmail());

        authService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful. Please check your email to confirm your account."));
    }

    /**
     * Login user and return JWT tokens.
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT access token and refresh token")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        LoginResponse response = authService.authenticateUser(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Refresh JWT access token using refresh token.
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Get a new access token using refresh token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        log.info("Token refresh attempt");

        LoginResponse response = authService.refreshToken(request.getRefreshToken());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Logout user (blacklist refresh token).
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Logout and invalidate refresh token")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestHeader("Authorization") String bearerToken) {
        log.info("Logout attempt");

        String token = bearerToken.replace("Bearer ", "");
        authService.logoutUser(token);

        return ResponseEntity.ok(ApiResponse.success("Logout successful"));
    }

    /**
     * Request password reset.
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset", description = "Send password reset link to email")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @RequestParam String email) {
        log.info("Password reset request for email: {}", email);

        authService.requestPasswordReset(email);

        return ResponseEntity.ok(ApiResponse.success("Password reset link sent to your email"));
    }

    /**
     * Reset password with token.
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password using token from email")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        log.info("Password reset attempt");

        authService.resetPassword(token, newPassword);

        return ResponseEntity.ok(ApiResponse.success("Password reset successful"));
    }
}