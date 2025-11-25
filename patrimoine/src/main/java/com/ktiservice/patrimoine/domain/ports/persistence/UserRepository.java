package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for User repository.
 * Defines contract for user persistence operations.
 */
public interface UserRepository {

    /**
     * Save user.
     */
    User save(User user);

    /**
     * Find user by ID.
     */
    Optional<User> findById(UUID userId);

    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Update user.
     */
    User update(User user);

    /**
     * Delete user by ID.
     */
    void deleteById(UUID userId);

    /**
     * Check if email exists.
     */
    boolean existsByEmail(String email);
}

