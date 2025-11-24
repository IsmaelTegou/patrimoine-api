package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.Guide;

import java.util.List;
import java.util.UUID;

/**
 * Use cases for Guide.
 */
public interface GuideService {

    Guide create(Guide guide);

    Guide update(UUID id, Guide guide);

    Guide getById(UUID id);

    List<Guide> getAll();

    void delete(UUID id);
}
