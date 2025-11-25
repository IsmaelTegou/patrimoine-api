package com.ktiservice.patrimoine.domain.events;

import lombok.Getter;

import java.util.UUID;

/**
 * Domain event published when a user registers.
 */
@Getter
public class UserRegisteredEvent extends DomainEvent {

    private final UUID userId;
    private final String email;
    private final String firstName;
    private final String lastName;

    public UserRegisteredEvent(UUID userId, String email, String firstName, String lastName) {
        super("USER_REGISTERED");
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}

