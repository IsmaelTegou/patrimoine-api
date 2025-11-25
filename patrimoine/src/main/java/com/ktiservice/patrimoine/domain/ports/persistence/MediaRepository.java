package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Media repository.
 */
public interface MediaRepository {
    Media save(Media media);
    Optional<Media> findById(UUID id);
    Media update(Media media);
    void softDelete(UUID id);
    Page<Media> findByHeritageNetworkId(UUID heritageNetworkId, Pageable pageable);
}
