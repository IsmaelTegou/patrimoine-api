package com.ktiservice.patrimoine.application.services;

import com.ktiservice.patrimoine.domain.entities.User;
import com.ktiservice.patrimoine.domain.enums.Role;
import com.ktiservice.patrimoine.domain.exceptions.InvalidOperationException;
import com.ktiservice.patrimoine.domain.exceptions.ValidationException;
import com.ktiservice.patrimoine.domain.ports.persistence.UserRepository;
import com.ktiservice.patrimoine.infrastructure.web.dto.auth.LoginResponse;
import com.ktiservice.patrimoine.infrastructure.web.dto.auth.RegisterRequest;
import com.ktiservice.patrimoine.infrastructure.web.security.JwtTokenProvider;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.RefreshTokenRepository;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.PasswordResetTokenRepository;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.RefreshTokenEntity;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.PasswordResetTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * Authentication service for user login, registration, and token management.
 */
@Slf4j
@Service
@Transactional
public class AuthenticationApplicationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthenticationApplicationService(UserRepository userRepository,
                                            JwtTokenProvider jwtTokenProvider,
                                            PasswordEncoder passwordEncoder,
                                            RefreshTokenRepository refreshTokenRepository,
                                            PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    /**
     * Register a new user.
     */
    public void registerUser(RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already registered");
        }

        User user = User.create(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName());

        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.TOURIST);
        user.setActive(false);  // Require email confirmation

        userRepository.save(user);
        log.info("User registered successfully: {}", request.getEmail());
    }

    /**
     * Authenticate user and return JWT tokens.
     */
    public LoginResponse authenticateUser(String email, String password) {
        log.info("Authenticating user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidOperationException("Invalid email or password"));

        if (!user.isActive()) {
            throw new InvalidOperationException("Account not activated. Please verify your email.");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidOperationException("Invalid email or password");
        }

        String accessToken = jwtTokenProvider.generateJwtToken(user.getId().toString(), email, user.getRole().toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);

        // Save refresh token
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .tokenHash(hashRefreshToken(refreshToken))
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(token);

        log.info("User authenticated successfully: {}", email);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(86400000L)  // 24 hours
                .userId(user.getId().toString())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .tokenType("Bearer")
                .build();
    }

    /**
     * Refresh access token.
     */
    public LoginResponse refreshToken(String refreshToken) {
        log.info("Refreshing token");

        // Validate refresh token exists and not expired
        boolean isValid = refreshTokenRepository.existsValidToken(refreshToken);
        if (!isValid) {
            throw new InvalidOperationException("Invalid or expired refresh token");
        }

        User user = userRepository.findByEmail(jwtTokenProvider.getEmailFromToken(refreshToken))
                .orElseThrow(() -> new InvalidOperationException("User not found"));

        String newAccessToken = jwtTokenProvider.generateJwtToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().toString());

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .expiresIn(86400000L)
                .userId(user.getId().toString())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .tokenType("Bearer")
                .build();
    }

    /**
     * Logout user (revoke refresh token).
     */
    public void logoutUser(String accessToken) {
        log.info("Logging out user");
        // Revoke refresh token if needed
    }

    /**
     * Request password reset.
     */
    public void requestPasswordReset(String email) {
        log.info("Password reset requested for: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("User not found"));

        String resetToken = UUID.randomUUID().toString();
        PasswordResetTokenEntity token = PasswordResetTokenEntity.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .tokenHash(hashRefreshToken(resetToken))
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();

        passwordResetTokenRepository.save(token);
    }

    /**
     * Reset password with token.
     */
    public void resetPassword(String token, String newPassword) {
        log.info("Resetting password");

        if (newPassword == null || newPassword.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters");
        }

        boolean isValid = passwordResetTokenRepository.existsValidToken(token);
        if (!isValid) {
            throw new InvalidOperationException("Invalid or expired reset token");
        }

        // TODO: Find user from token and update password
    }

    public String hashRefreshToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash token", e);
        }
    }
}
