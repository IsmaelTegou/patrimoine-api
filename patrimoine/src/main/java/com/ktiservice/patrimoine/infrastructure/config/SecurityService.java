package com.ktiservice.patrimoine.infrastructure.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Security utility service for authorization checks.
 * Used in @PreAuthorize expressions.
 */
@Service
public class SecurityService {

    /**
     * Check if authenticated user is the owner of the resource.
     */
    public boolean isOwner(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String principal = authentication.getPrincipal() == null ? null : authentication.getPrincipal().toString();
        if (principal == null) return false;

        try {
            UUID authenticatedUserId = UUID.fromString(principal);
            return authenticatedUserId.equals(userId);
        } catch (IllegalArgumentException ex) {
            // principal is not a UUID (fallback)
            return false;
        }
    }

    /**
     * Check if user has a specific role.
     */
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + role));
    }

    /**
     * Get current authenticated user ID.
     */
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return UUID.fromString(authentication.getPrincipal().toString());
    }

    /**
     * Get current authenticated user email.
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }
}
