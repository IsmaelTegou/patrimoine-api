package com.ktiservice.patrimoine.repository.media;

import com.ktiservice.patrimoine.entities.MediaJpaEntity;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class MediaRepositoryAdapter implements MediaRepository {

    private final MediaJpaRepository mediaJpaRepository;

    public MediaRepositoryAdapter(MediaJpaRepository mediaJpaRepository) {
        this.mediaJpaRepository = mediaJpaRepository;
    }

    @Override
    public Media save(Media media) {
        MediaJpaEntity saved = mediaJpaRepository.save(MediaJpaEntity.fromDomainEntity(media));
        log.debug("Media saved: {}", saved.getId());
        return saved.toDomainEntity();
    }

    @Override
    public Optional<Media> findById(UUID id) {
        return mediaJpaRepository.findById(id)
                .map(MediaJpaEntity::toDomainEntity);
    }

    @Override
    public List<Media> findAll() {
        return mediaJpaRepository.findAll()
                .stream()
                .map(MediaJpaEntity::toDomainEntity)
                .toList();
    }

    @Override
    public List<Media> findByHeritageNetworkId(UUID heritageNetworkId) {
        return mediaJpaRepository.findByHeritageNetworkId(heritageNetworkId)
                .stream()
                .map(MediaJpaEntity::toDomainEntity)
                .toList();
    }

    @Override
    public Media update(Media media) {
        if (!mediaJpaRepository.existsById(media.getId())) {
            throw new ResourceNotFoundException("Media", media.getId().toString());
        }

        MediaJpaEntity updated = mediaJpaRepository.save(
                MediaJpaEntity.fromDomainEntity(media)
        );
        log.debug("Media updated: {}", updated.getId());
        return updated.toDomainEntity();
    }

    @Override
    public void delete(UUID id) {
        MediaJpaEntity existing = mediaJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media", id.toString()));

        existing.setDeletedAt(LocalDateTime.now());
        mediaJpaRepository.save(existing);

        log.debug("Media soft-deleted: {}", id);
    }
}
