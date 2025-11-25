package com.ktiservice.patrimoine.repository.media;

import com.ktiservice.patrimoine.models.Media;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository {

    Media save(Media media);

    Optional<Media> findById(UUID id);

    List<Media> findAll();

    List<Media> findByHeritageNetworkId(UUID heritageNetworkId);

    Media update(Media media);

    void delete(UUID id);
}
