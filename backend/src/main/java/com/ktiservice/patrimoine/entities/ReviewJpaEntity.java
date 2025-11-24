package com.ktiservice.patrimoine.entities;

import com.ktiservice.patrimoine.models.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity for Reviews.
 * Maps to reviews table with soft delete support.
 */
@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_reviews_site_id", columnList = "heritage_site_id"),
        @Index(name = "idx_reviews_user_id", columnList = "user_id"),
        @Index(name = "idx_reviews_is_approved", columnList = "is_approved"),
        @Index(name = "idx_reviews_created_at", columnList = "created_at")
})
@Where(clause = "deleted_at IS NULL")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reviews_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReviewJpaEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "heritage_site_id", nullable = false, columnDefinition = "UUID")
    private UUID siteId;

    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

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
    public Review toDomainEntity() {
        var domain = new Review();
        domain.setId(this.id);
        domain.setSiteId(this.siteId);
        domain.setUserId(this.userId);
        domain.setRating(this.rating);
        domain.setComment(this.comment);
        domain.setApproved(this.isApproved);
        domain.setCreatedAt(this.createdAt);
        domain.setCreatedBy(this.createdBy);
        domain.setUpdatedAt(this.updatedAt);
        domain.setUpdatedBy(this.updatedBy);
        return domain;
    }

    /**
     * Create JPA entity from domain entity.
     */
    public static ReviewJpaEntity fromDomainEntity(Review domain) {
        return ReviewJpaEntity.builder()
                .id(domain.getId())
                .siteId(domain.getSiteId())
                .userId(domain.getUserId())
                .rating(domain.getRating())
                .comment(domain.getComment())
                .isApproved(domain.isApproved())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .build();
    }
}
