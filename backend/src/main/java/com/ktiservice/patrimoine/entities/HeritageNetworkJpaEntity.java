package com.ktiservice.patrimoine.entities;

import com.ktiservice.patrimoine.enums.ConservationState;
import com.ktiservice.patrimoine.enums.HeritageType;
import com.ktiservice.patrimoine.models.HeritageNetwork;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * JPA Entity for Heritage Network sites.
 * Maps to heritage_sites table with soft delete support.
 */
@Entity
@Table(name = "heritage_sites", indexes = {
        @Index(name = "idx_heritage_sites_province", columnList = "province"),
        @Index(name = "idx_heritage_sites_heritage_type", columnList = "heritage_type"),
        @Index(name = "idx_heritage_sites_average_rating", columnList = "average_rating"),
        @Index(name = "idx_heritage_sites_created_at", columnList = "created_at")
})
@Where(clause = "deleted_at IS NULL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageNetworkJpaEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String siteName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HeritageType heritageType;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 100)
    private String province;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(name = "entry_fee", precision = 10, scale = 2)
    private BigDecimal entryFee;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "conservation_state")
    private ConservationState conservationState;

    @Column(name = "manager_contact_name", length = 150)
    private String managerContactName;

    @Column(name = "manager_phone_number", length = 20)
    private String managerPhoneNumber;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @Column(name = "total_visits")
    private Integer totalVisits;

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

    public HeritageNetworkJpaEntity(UUID id) {
        this.id = id;
    }

    /**
     * Convert JPA entity to domain entity.
     */
     public HeritageNetwork toDomainEntity() {
        var domain = new HeritageNetwork();
        domain.setId(this.id);
        domain.setSiteName(this.siteName);
        domain.setDescription(this.description);
        domain.setHeritageType(this.heritageType);
        domain.setLatitude(this.latitude);
        domain.setLongitude(this.longitude);
        domain.setProvince(this.province);
        domain.setOpeningTime(this.openingTime);
        domain.setClosingTime(this.closingTime);
        domain.setEntryFee(this.entryFee);
        domain.setMaxCapacity(this.maxCapacity);
        domain.setConservationState(this.conservationState);
        domain.setManagerContactName(this.managerContactName);
        domain.setManagerPhoneNumber(this.managerPhoneNumber);
        domain.setAverageRating(this.averageRating);
        domain.setTotalReviews(this.totalReviews);
        domain.setTotalVisits(this.totalVisits);
        domain.setCreatedAt(this.createdAt);
        domain.setCreatedBy(this.createdBy);
        domain.setUpdatedAt(this.updatedAt);
        domain.setUpdatedBy(this.updatedBy);
        return domain;
    }

    /**
     * Create JPA entity from domain entity.
     */
    public static HeritageNetworkJpaEntity fromDomainEntity(HeritageNetwork domain) {
        return HeritageNetworkJpaEntity.builder()
                .id(domain.getId())
                .siteName(domain.getSiteName())
                .description(domain.getDescription())
                .heritageType(domain.getHeritageType())
                .latitude(domain.getLatitude())
                .longitude(domain.getLongitude())
                .province(domain.getProvince())
                .openingTime(domain.getOpeningTime())
                .closingTime(domain.getClosingTime())
                .entryFee(domain.getEntryFee())
                .maxCapacity(domain.getMaxCapacity())
                .conservationState(domain.getConservationState())
                .managerContactName(domain.getManagerContactName())
                .managerPhoneNumber(domain.getManagerPhoneNumber())
                .averageRating(domain.getAverageRating())
                .totalReviews(domain.getTotalReviews())
                .totalVisits(domain.getTotalVisits())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .updatedAt(domain.getUpdatedAt())
                .updatedBy(domain.getUpdatedBy())
                .build();
    }
}

