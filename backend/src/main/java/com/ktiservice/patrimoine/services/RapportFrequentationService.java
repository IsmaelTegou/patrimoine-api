package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.RapportFrequentation;

import java.util.List;
import java.util.UUID;

/**
 * Application service interface for RapportFrequentation.
 */
public interface RapportFrequentationService {

    RapportFrequentation create(RapportFrequentation rapport);

    RapportFrequentation update(UUID id, RapportFrequentation rapport);

    RapportFrequentation getById(UUID id);

    List<RapportFrequentation> getAll();

    void delete(UUID id);
}
