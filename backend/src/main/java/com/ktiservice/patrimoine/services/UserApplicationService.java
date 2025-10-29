//package com.ktiservice.patrimoine.service;
//
//import com.ktiservice.patrimoine.models.User;
//import com.ktiservice.patrimoine.enums.Role;
//import com.ktiservice.patrimoine.exceptions.InvalidOperationException;
//import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
//import com.ktiservice.patrimoine.exceptions.ValidationException;
//import com.ktiservice.patrimoine.mappers.UserMapper;
//import com.ktiservice.patrimoine.dto.users.CreateUserRequest;
//import com.ktiservice.patrimoine.dto.users.UserResponse;
//import com.ktiservice.patrimoine.repository.user.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
///**
// * Application service for User operations.
// * Orchestrates business logic and persistence operations.
// * Uses annotation-based transactions and caching.
// */
//@Slf4j
//@Service
//@Transactional
//public class UserApplicationService {
//
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserApplicationService(UserRepository userRepository,
//                                  UserMapper userMapper,
//                                  PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    /**
//     * Register a new user (tourist).
//     *
//     * @param request User registration request
//     * @param adminEmail Email of admin who creates the account (for audit)
//     * @return Registered user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", allEntries = true)
//    public UserResponse registerUser(CreateUserRequest request, String adminEmail) {
//        log.info("Attempting to register new user with email: {}", request.getEmail());
//
//        // Check if email already exists
//        if (userRepository.existsByEmail(request.getEmail())) {
//            log.warn("Email already registered: {}", request.getEmail());
//            throw new InvalidOperationException(
//                    String.format("Email %s is already registered", request.getEmail()),
//                    "EMAIL_ALREADY_EXISTS"
//            );
//        }
//
//        // Hash password
//        String passwordHash = passwordEncoder.encode(request.getPassword());
//
//        // Create user from request
//        User user = userMapper.fromCreateRequest(request);
//        user.setPasswordHash(passwordHash);
//        user.setRole(Role.TOURIST);
//        user.setCreatedBy(adminEmail);
//
//        // Save user
//        User savedUser = userRepository.save(user);
//        log.info("User registered successfully with email: {}", savedUser.getEmail());
//
//        return userMapper.toResponse(savedUser);
//    }
//
//    /**
//     * Find user by ID.
//     *
//     * @param userId User ID
//     * @return User response
//     */
//    @Transactional(readOnly = true)
//    @Cacheable(value = "users", key = "#userId")
//    public UserResponse getUserById(UUID userId) {
//        log.debug("Fetching user by ID: {}", userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        return userMapper.toResponse(user);
//    }
//
//    /**
//     * Find user by email.
//     *
//     * @param email User email
//     * @return User response
//     */
//    @Transactional(readOnly = true)
//    public UserResponse getUserByEmail(String email) {
//        log.debug("Fetching user by email: {}", email);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User", email));
//
//        return userMapper.toResponse(user);
//    }
//
//    /**
//     * Update user information.
//     *
//     * @param userId User ID
//     * @param firstName New first name
//     * @param lastName New last name
//     * @param phoneNumber New phone number
//     * @param modifiedBy Email of user who made the modification
//     * @return Updated user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public UserResponse updateUser(UUID userId, String firstName, String lastName,
//                                   String phoneNumber, String modifiedBy) {
//        log.info("Updating user: {}", userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        user.updateInfo(firstName, lastName, phoneNumber, null);
//        user.setUpdatedBy(modifiedBy);
//
//        User updatedUser = userRepository.update(user);
//        log.info("User updated successfully: {}", userId);
//
//        return userMapper.toResponse(updatedUser);
//    }
//
//    /**
//     * Activate user account.
//     *
//     * @param userId User ID
//     * @param activatedBy Email of admin who activated
//     * @return Updated user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public UserResponse activateUser(UUID userId, String activatedBy) {
//        log.info("Activating user: {}", userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        if (user.isActive()) {
//            throw new InvalidOperationException("User is already active");
//        }
//
//        user.activate();
//        user.setUpdatedBy(activatedBy);
//
//        User updatedUser = userRepository.update(user);
//        log.info("User activated successfully: {}", userId);
//
//        return userMapper.toResponse(updatedUser);
//    }
//
//    /**
//     * Deactivate user account.
//     *
//     * @param userId User ID
//     * @param deactivatedBy Email of admin who deactivated
//     * @return Updated user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public UserResponse deactivateUser(UUID userId, String deactivatedBy) {
//        log.info("Deactivating user: {}", userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        if (!user.isActive()) {
//            throw new InvalidOperationException("User is already inactive");
//        }
//
//        user.deactivate();
//        user.setUpdatedBy(deactivatedBy);
//
//        User updatedUser = userRepository.update(user);
//        log.info("User deactivated successfully: {}", userId);
//
//        return userMapper.toResponse(updatedUser);
//    }
//
//    /**
//     * Assign role to user.
//     *
//     * @param userId User ID
//     * @param role New role
//     * @param modifiedBy Email of admin who modified
//     * @return Updated user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public UserResponse assignRoleToUser(UUID userId, Role role, String modifiedBy) {
//        log.info("Assigning role {} to user: {}", role, userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        if (role == null) {
//            throw new ValidationException("Role cannot be null");
//        }
//
//        user.assignRole(role);
//        user.setUpdatedBy(modifiedBy);
//
//        User updatedUser = userRepository.update(user);
//        log.info("Role assigned successfully to user: {}", userId);
//
//        return userMapper.toResponse(updatedUser);
//    }
//
//    /**
//     * Change user password.
//     *
//     * @param userId User ID
//     * @param newPassword New plain text password
//     * @param changedBy Email of user who changed password
//     * @return Updated user response
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public UserResponse changePassword(UUID userId, String newPassword, String changedBy) {
//        log.info("Changing password for user: {}", userId);
//
//        if (newPassword == null || newPassword.length() < 8) {
//            throw new ValidationException("Password must be at least 8 characters");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        String passwordHash = passwordEncoder.encode(newPassword);
//        user.setPasswordHash(passwordHash);
//        user.setUpdatedBy(changedBy);
//
//        User updatedUser = userRepository.update(user);
//        log.info("Password changed successfully for user: {}", userId);
//
//        return userMapper.toResponse(updatedUser);
//    }
//
//    /**
//     * Verify user credentials.
//     *
//     * @param email User email
//     * @param rawPassword Raw password to verify
//     * @return true if credentials are valid
//     */
//    @Transactional(readOnly = true)
//    public boolean verifyCredentials(String email, String rawPassword) {
//        log.debug("Verifying credentials for email: {}", email);
//
//        return userRepository.findByEmail(email)
//                .map(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()))
//                .orElse(false);
//    }
//
//    /**
//     * Delete user account.
//     *
//     * @param userId User ID
//     * @param deletedBy Email of admin who deleted
//     */
//    @Transactional
//    @CacheEvict(value = "users", key = "#userId")
//    public void deleteUser(UUID userId, String deletedBy) {
//        log.info("Deleting user: {}", userId);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
//
//        userRepository.deleteById(userId);
//        log.info("User deleted successfully: {}", userId);
//    }
//}
