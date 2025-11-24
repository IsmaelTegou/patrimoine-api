package com.ktiservice.patrimoine.repository.guide;

import com.ktiservice.patrimoine.models.Guide;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Guide repository.
 * Defines contract for persistence operations.
 */
public interface GuideRepository {

    Guide save(Guide guide);

    Optional<Guide> findById(UUID id);

    List<Guide> findAll();

    Guide update(Guide guide);

    void deleteById(UUID id);
}
