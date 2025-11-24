package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.GestionnaireSite;

import java.util.List;
import java.util.UUID;

/**
 * Application service interface for GestionnaireSite.
 */
public interface GestionnaireSiteService {

    GestionnaireSite create(GestionnaireSite gs);

    GestionnaireSite update(UUID id, GestionnaireSite gs);

    GestionnaireSite getById(UUID id);

    List<GestionnaireSite> getAll();

    void delete(UUID id);
}
