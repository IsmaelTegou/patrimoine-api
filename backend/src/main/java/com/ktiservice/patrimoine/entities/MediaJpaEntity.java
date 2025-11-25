package com.ktiservice.patrimoine.entities;

import com.ktiservice.patrimoine.enums.MediaType;
import com.ktiservice.patrimoine.models.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity for Media files (photos, videos).
 * Maps to media table with soft delete support.
 */
@Entity
@Table(name = "media", indexes = {
        @Index(name = "idx_media_heritage_site_id", columnList = "heritage_site_id"),
        @Index(name = "idx_media_media_type", columnList = "media_type"),
        @Index(name = "idx_media_uploaded_at", columnList = "uploaded_at")
})
@Where(clause = "deleted_at IS NULL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaJpaEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "heritage_site_id", nullable = false, columnDefinition = "UUID")
    private UUID heritageNetworkId;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "minio_path", nullable = false, length = 500)
    private String minioPath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "access_url", length = 500)
    private String accessUrl;

    @Column(name = "version_number")
    private Integer versionNumber;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public Media toDomainEntity() {
        return Media.builder()
                .heritageNetworkId(this.heritageNetworkId)
                .mediaType(this.mediaType)
                .fileName(this.fileName)
                .minioPath(this.minioPath)
                .fileSize(this.fileSize)
                .description(this.description)
                .accessUrl(this.accessUrl)
                .versionNumber(this.versionNumber)
                .build();
    }

    public static MediaJpaEntity fromDomainEntity(Media media) {
        if (media == null) {
            return null;
        }

        return MediaJpaEntity.builder()
                .id(media.getId() != null ? media.getId() : UUID.randomUUID())
                .heritageNetworkId(media.getHeritageNetworkId())
                .mediaType(media.getMediaType())
                .fileName(media.getFileName())
                .minioPath(media.getMinioPath())
                .fileSize(media.getFileSize())
                .description(media.getDescription())
                .accessUrl(media.getAccessUrl())
                .versionNumber(media.getVersionNumber())
                .build();
    }

}