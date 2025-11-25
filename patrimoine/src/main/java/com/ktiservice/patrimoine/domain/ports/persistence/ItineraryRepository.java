package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Itinerary repository.
 */
public interface ItineraryRepository {
    Itinerary save(Itinerary itinerary);
    Optional<Itinerary> findById(UUID id);
    Itinerary update(Itinerary itinerary);
    void softDelete(UUID id);
    Page<Itinerary> findAll(Pageable pageable);
    Page<Itinerary> findByTheme(String theme, Pageable pageable);
}
