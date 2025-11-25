package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.Media;

import java.util.List;
import java.util.UUID;

public interface MediaService {

    Media upload(Media media);

    Media update(UUID id, Media media);

    Media getById(UUID id);

    List<Media> getAll();

    List<Media> getByHeritageNetwork(UUID heritageSiteId);

    List<Media> getBySite(UUID siteId);

    void delete(UUID id);
}
