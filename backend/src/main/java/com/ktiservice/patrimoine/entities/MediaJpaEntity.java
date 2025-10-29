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

    /**
     * Convert JPA entity to domain entity.
     */
    public Media toDomainEntity() {
        var domain = new Media();
        domain.setId(this.id);
        domain.setHeritageNetworkId(this.heritageNetworkId);
        domain.setMediaType(this.mediaType);
        domain.setFileName(this.fileName);
        domain.setMinioPath(this.minioPath);
        domain.setFileSize(this.fileSize);
        domain.setDescription(this.description);
        domain.setAccessUrl(this.accessUrl);
        domain.setVersionNumber(this.versionNumber);
        domain.setCreatedAt(this.uploadedAt);
        return domain;
    }

    /**
     * Create JPA entity from domain entity.
     */
    public static MediaJpaEntity fromDomainEntity(Media domain) {
        return MediaJpaEntity.builder()
                .id(domain.getId())
                .heritageNetworkId(domain.getHeritageNetworkId())
                .mediaType(domain.getMediaType())
                .fileName(domain.getFileName())
                .minioPath(domain.getMinioPath())
                .fileSize(domain.getFileSize())
                .description(domain.getDescription())
                .accessUrl(domain.getAccessUrl())
                .versionNumber(domain.getVersionNumber())
                .build();
    }
}
