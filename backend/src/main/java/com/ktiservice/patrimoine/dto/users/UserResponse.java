package com.ktiservice.patrimoine.dto.users;

import com.ktiservice.patrimoine.enums.Language;
import com.ktiservice.patrimoine.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for user response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User response")
public class UserResponse {

    @Schema(description = "User unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "User first name", example = "Jean")
    private String firstName;

    @Schema(description = "User last name", example = "Dupont")
    private String lastName;

    @Schema(description = "User phone number", example = "+237123456789")
    private String phoneNumber;

    @Schema(description = "User role", example = "TOURIST")
    private Role role;

    @Schema(description = "Preferred language", example = "FRENCH")
    private Language language;

    @Schema(description = "Is user account active", example = "true")
    private boolean isActive;

    @Schema(description = "Account creation timestamp")
    private LocalDateTime createdAt;
}
