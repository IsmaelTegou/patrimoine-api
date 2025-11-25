package com.ktiservice.patrimoine.infrastructure.web.controllers;

import com.ktiservice.patrimoine.application.services.UserApplicationService;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.ApiResponse;
import com.ktiservice.patrimoine.infrastructure.web.dto.users.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for User management operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserApplicationService userService;

    public UserController(UserApplicationService userService) {
        this.userService = userService;
    }

    /**
     * Get current authenticated user profile.
     */
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('TOURIST', 'GUIDE', 'SITE_MANAGER', 'ADMINISTRATOR')")
    @Operation(summary = "Get user profile", description = "Retrieve current authenticated user's profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserProfile(
            Authentication authentication) {
        log.info("Fetching profile for user: {}", authentication.getName());

        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        UserResponse response = userService.getUserById(userId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Get user by ID (admin only).
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(summary = "Get user by ID", description = "Retrieve user details by ID (Admin only)")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable UUID userId) {
        log.info("Fetching user: {}", userId);

        UserResponse response = userService.getUserById(userId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Update user profile.
     */
    @PutMapping("/profile")
    @PreAuthorize("hasAnyRole('TOURIST', 'GUIDE', 'SITE_MANAGER', 'ADMINISTRATOR')")
    @Operation(summary = "Update user profile", description = "Update current user's profile information")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserProfile(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNumber,
            Authentication authentication) {
        log.info("Updating profile for user: {}", authentication.getName());

        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        UserResponse response = userService.updateUser(userId, firstName, lastName, phoneNumber,
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Change password.
     */
    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('TOURIST', 'GUIDE', 'SITE_MANAGER', 'ADMINISTRATOR')")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestParam String newPassword,
            Authentication authentication) {
        log.info("Password change requested for user: {}", authentication.getName());

        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        userService.changePassword(userId, newPassword, authentication.getName());

        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

    /**
     * Delete user account (self-delete or admin).
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isOwner(#userId)")
    @Operation(summary = "Delete user account", description = "Delete user account (Admin or own account)")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable UUID userId,
            Authentication authentication) {
        log.info("Deleting user: {}", userId);

        UUID authenticatedUserId = UUID.fromString(authentication.getPrincipal().toString());
        userService.deleteUser(userId, authenticatedUserId.toString());

        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }

    /**
     * Activate user account (admin only).
     */
    @PutMapping("/{userId}/activate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(summary = "Activate user", description = "Activate a user account (Admin only)")
    public ResponseEntity<ApiResponse<UserResponse>> activateUser(
            @PathVariable UUID userId,
            Authentication authentication) {
        log.info("Activating user: {}", userId);

        UUID adminId = UUID.fromString(authentication.getPrincipal().toString());
        UserResponse response = userService.activateUser(userId, authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Deactivate user account (admin only).
     */
    @PutMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(summary = "Deactivate user", description = "Deactivate a user account (Admin only)")
    public ResponseEntity<ApiResponse<UserResponse>> deactivateUser(
            @PathVariable UUID userId,
            Authentication authentication) {
        log.info("Deactivating user: {}", userId);

        UUID adminId = UUID.fromString(authentication.getPrincipal().toString());
        UserResponse response = userService.deactivateUser(userId, authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

