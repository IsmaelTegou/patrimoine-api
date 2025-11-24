package com.ktiservice.patrimoine.repository.rapportFrequentation;

import com.ktiservice.patrimoine.models.RapportFrequentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for RapportFrequentation repository.
 */
public interface RapportFrequentationRepository {

    RapportFrequentation save(RapportFrequentation rapport);

    Optional<RapportFrequentation> findById(UUID id);

    List<RapportFrequentation> findAll();

    RapportFrequentation update(RapportFrequentation rapport);

    void deleteById(UUID id);
}
