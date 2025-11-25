package com.ktiservice.patrimoine.repository.media;

import com.ktiservice.patrimoine.entities.MediaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaJpaRepository extends JpaRepository<MediaJpaEntity, UUID> {

    List<MediaJpaEntity> findByHeritageNetworkId(UUID heritageSiteId);

    List<MediaJpaEntity> findByMediaType(String mediaType);
}
