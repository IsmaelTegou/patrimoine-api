package com.ktiservice.patrimoine.serviceImplementation;

import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.exceptions.ValidationException;
import com.ktiservice.patrimoine.models.Media;
import com.ktiservice.patrimoine.repository.media.MediaRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.ktiservice.patrimoine.services.MediaService;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media upload(Media media) {
        validate(media);
        return mediaRepository.save(media);
    }

    @Override
    public Media update(UUID id, Media media) {
        Media existing = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media", id.toString()));

        validate(media);

        // Conserver valeurs système
        media.setId(existing.getId());
        media.setCreatedAt(existing.getCreatedAt());

        return mediaRepository.update(media);
    }

    @Override
    public Media getById(UUID id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media", id.toString()));
    }

    @Override
    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    @Override
    public List<Media> getByHeritageNetwork(UUID siteId) {
        return mediaRepository.findByHeritageNetworkId(siteId);
    }

    @Override
    public List<Media> getBySite(UUID siteId) {
        return mediaRepository.findByHeritageNetworkId(siteId);
    }

    @Override
    public void delete(UUID id) {
        mediaRepository.delete(id);
    }

    // ---------------- VALIDATIONS ----------------

    private void validate(Media media) {

        if (media.getHeritageNetworkId() == null) {
            throw new ValidationException("HeritageSiteId cannot be null");
        }

        if (media.getMediaType() == null) {
            throw new ValidationException("MediaType cannot be null");
        }

        if (media.getFileName() == null || media.getFileName().isBlank()) {
            throw new ValidationException("FileName cannot be empty");
        }

        if (media.getMinioPath() == null || media.getMinioPath().isBlank()) {
            throw new ValidationException("Minio path is required");
        }

        if (media.getFileSize() == null || media.getFileSize() <= 0) {
            throw new ValidationException("Invalid file size");
        }
    }
}
