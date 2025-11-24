package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.enums.Language;
import com.ktiservice.patrimoine.enums.Role;
import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.*;

import java.util.regex.Pattern;

/**
 * User domain entity.
 * Represents a user in the heritage management system.
 */

@Getter
@Setter 
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private Language language;
    private boolean isActive;

    // Email validation regex
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Private constructor for creation via factory methods.
     */
    private User(String email, String password, String firstName, String lastName) {
        super();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = Role.TOURIST;
        this.language = Language.FRENCH;
        this.isActive = true;
    }

    /**
     * Factory method to create a new User.
     *
     * @param email User email
     * @param passwordHash BCrypt hashed password
     * @param firstName User first name
     * @param lastName User last name
     * @return New User instance
     */
    public static User create(String email, String passwordHash, String firstName, String lastName) {
        // Validate inputs
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            throw new ValidationException("Password hash cannot be empty");
        }

        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name cannot be empty");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name cannot be empty");
        }

        return new User(email, passwordHash, firstName, lastName);
    }

    /**
     * Update user information.
     */
    public void updateInfo(String firstName, String lastName, String phoneNumber, Language language) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        }

        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        }

        if (phoneNumber != null && !phoneNumber.isBlank()) {
            this.phoneNumber = phoneNumber;
        }

        if (language != null) {
            this.language = language;
        }
    }

    /**
     * Activate user account.
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * Deactivate user account.
     */
    public void deactivate() {
        this.isActive = false;
    }

    /**
     * Assign role to user.
     */
    public void assignRole(Role role) {
        if (role == null) {
            throw new ValidationException("Role cannot be null");
        }
        this.role = role;
    }

    /**
     * Get full name of user.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Check if user has specific role.
     */
    public boolean hasRole(Role role) {
        return this.role == role;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    public String getPasswordHash() {
        return this.password;
    }
}

