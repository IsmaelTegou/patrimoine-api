package com.ktiservice.patrimoine.infrastructure.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Login response with JWT tokens")
public class LoginResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh token for token renewal")
    private String refreshToken;

    @Schema(description = "Token expiration time in milliseconds")
    private Long expiresIn;

    @Schema(description = "User ID")
    private String userId;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "User role")
    private String role;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;
}

