package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Event repository.
 */
public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(UUID id);
    Event update(Event event);
    void softDelete(UUID id);
    Page<Event> findByHeritageNetworkId(UUID heritageNetworkId, Pageable pageable);
    Page<Event> findUpcoming(LocalDateTime fromDate, Pageable pageable);
    Page<Event> findByEventType(String eventType, Pageable pageable);
}
