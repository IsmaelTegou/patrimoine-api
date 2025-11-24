package com.ktiservice.patrimoine.repository.gestionnaireSite;

import com.ktiservice.patrimoine.models.GestionnaireSite;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for GestionnaireSite repository.
 */
public interface GestionnaireSiteRepository {

    GestionnaireSite save(GestionnaireSite gs);

    Optional<GestionnaireSite> findById(UUID id);

    List<GestionnaireSite> findAll();

    GestionnaireSite update(GestionnaireSite gs);

    void deleteById(UUID id);
}
