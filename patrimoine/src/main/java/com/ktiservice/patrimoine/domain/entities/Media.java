package com.ktiservice.patrimoine.domain.entities;

import com.ktiservice.patrimoine.domain.enums.MediaType;
import com.ktiservice.patrimoine.domain.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Media domain entity.
 * Represents a photo or video associated with a heritage site.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Media extends BaseEntity {

    private UUID heritageNetworkId;
    private MediaType mediaType;
    private String fileName;
    private String minioPath;
    private Long fileSize;
    private String description;
    private String accessUrl;
    private Integer versionNumber;

    /**
     * Factory method to create new Media.
     */
    public static Media create(UUID heritageNetworkId, MediaType mediaType, String fileName,
                               String minioPath, Long fileSize) {
        if (heritageNetworkId == null) {
            throw new ValidationException("Heritage network ID is required");
        }
        if (mediaType == null) {
            throw new ValidationException("Media type is required");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new ValidationException("File name is required");
        }
        if (minioPath == null || minioPath.isBlank()) {
            throw new ValidationException("MinIO path is required");
        }
        if (fileSize == null || fileSize <= 0) {
            throw new ValidationException("File size must be positive");
        }

        Media media = new Media();
        media.heritageNetworkId = heritageNetworkId;
        media.mediaType = mediaType;
        media.fileName = fileName;
        media.minioPath = minioPath;
        media.fileSize = fileSize;
        media.versionNumber = 1;
        return media;
    }

    /**
     * Increment version for versioning support.
     */
    public void incrementVersion() {
        this.versionNumber = (this.versionNumber != null ? this.versionNumber : 1) + 1;
    }

    /**
     * Update access URL (after MinIO presigned URL generated).
     */
    public void updateAccessUrl(String url) {
        this.accessUrl = url;
    }
}
