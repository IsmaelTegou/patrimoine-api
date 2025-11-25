package com.ktiservice.patrimoine.domain.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events.
 * Events are published when significant domain actions occur.
 */
@Getter
public abstract class DomainEvent {

    private final UUID eventId;
    private final LocalDateTime occurredAt;
    private final String eventType;

    protected DomainEvent(String eventType) {
        this.eventId = UUID.randomUUID();
        this.occurredAt = LocalDateTime.now();
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return eventType + " at " + occurredAt;
    }
}

