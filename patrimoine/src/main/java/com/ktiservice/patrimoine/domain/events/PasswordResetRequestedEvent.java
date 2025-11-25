package com.ktiservice.patrimoine.domain.events;

import lombok.Getter;

import java.util.UUID;

/**
 * Domain event published when user requests password reset.
 */
@Getter
public class PasswordResetRequestedEvent extends DomainEvent {

    private final UUID userId;
    private final String email;
    private final String resetToken;

    public PasswordResetRequestedEvent(UUID userId, String email, String resetToken) {
        super("PASSWORD_RESET_REQUESTED");
        this.userId = userId;
        this.email = email;
        this.resetToken = resetToken;
    }

}
