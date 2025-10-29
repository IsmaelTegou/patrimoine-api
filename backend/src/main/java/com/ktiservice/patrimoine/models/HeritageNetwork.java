package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.enums.ConservationState;
import com.ktiservice.patrimoine.enums.HeritageType;
import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Heritage network domain entity.
 * Represents a heritage site (natural, cultural, archaeological).
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeritageNetwork extends BaseEntity {

    private String siteName;
    private String description;
    private HeritageType heritageType;
    private Double latitude;
    private Double longitude;
    private String province;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private BigDecimal entryFee;
    private Integer maxCapacity;
    private ConservationState conservationState;
    private String managerContactName;
    private String managerPhoneNumber;
    private Double averageRating;
    private Integer totalReviews;
    private Integer totalVisits;

    /**
     * Factory method to create a new HeritageNetwork.
     */
    public static HeritageNetwork create(String siteName, String description, HeritageType heritageType,
                                         Double latitude, Double longitude, String province) {
        if (siteName == null || siteName.isBlank()) {
            throw new ValidationException("Site name cannot be empty");
        }
        if (heritageType == null) {
            throw new ValidationException("Heritage type is required");
        }
        if (latitude == null || longitude == null) {
            throw new ValidationException("Coordinates are required");
        }

        HeritageNetwork site = new HeritageNetwork();
        site.siteName = siteName;
        site.description = description;
        site.heritageType = heritageType;
        site.latitude = latitude;
        site.longitude = longitude;
        site.province = province;
        site.conservationState = ConservationState.GOOD;
        site.totalReviews = 0;
        site.totalVisits = 0;
        site.averageRating = 0.0;
        return site;
    }

    /**
     * Update site information.
     */
    public void updateInfo(String siteName, String description, LocalTime openingTime,
                           LocalTime closingTime, BigDecimal entryFee, Integer maxCapacity) {
        if (siteName != null && !siteName.isBlank()) {
            this.siteName = siteName;
        }
        if (description != null) {
            this.description = description;
        }
        if (openingTime != null) {
            this.openingTime = openingTime;
        }
        if (closingTime != null) {
            this.closingTime = closingTime;
        }
        if (entryFee != null) {
            this.entryFee = entryFee;
        }
        if (maxCapacity != null && maxCapacity > 0) {
            this.maxCapacity = maxCapacity;
        }
    }

    /**
     * Update conservation state.
     */
    public void updateConservationState(ConservationState state) {
        if (state != null) {
            this.conservationState = state;
        }
    }

    /**
     * Increment total visits.
     */
    public void incrementVisits() {
        this.totalVisits = (this.totalVisits != null ? this.totalVisits : 0) + 1;
    }

    /**
     * Update average rating (called from Review aggregation).
     */
    public void updateAverageRating(Double newAverage, Integer reviewCount) {
        this.averageRating = newAverage != null ? newAverage : 0.0;
        this.totalReviews = reviewCount != null ? reviewCount : 0;
    }
}
