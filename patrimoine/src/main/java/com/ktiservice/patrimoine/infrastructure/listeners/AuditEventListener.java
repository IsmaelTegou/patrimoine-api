package com.ktiservice.patrimoine.infrastructure.listeners;

import com.ktiservice.patrimoine.domain.events.DomainEvent;
import com.ktiservice.patrimoine.infrastructure.persistence.adapters.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Spring event listener for domain events.
 * Logs audit trail for all domain events.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditEventListener {

    private final AuditService auditService;

    /**
     * Listen to domain events for audit logging.
     */
    @EventListener
    public void handleDomainEvent(DomainEvent event) {
        log.debug("Domain event received: {}", event.getEventType());
        // Additional audit processing can be added here
    }
}
