package com.ktiservice.patrimoine.domain.entities;

import com.ktiservice.patrimoine.domain.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Itinerary domain entity.
 * Represents a curated route through multiple heritage sites.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Itinerary extends BaseEntity {

    private String title;
    private String description;
    private Integer estimatedDuration;  // in minutes
    private String theme;
    private List<UUID> siteSequence;  // Ordered list of site IDs

    /**
     * Factory method to create new Itinerary.
     */
    public static Itinerary create(String title, String description, String theme) {
        if (title == null || title.isBlank()) {
            throw new ValidationException("Itinerary title is required");
        }

        Itinerary itinerary = new Itinerary();
        itinerary.title = title;
        itinerary.description = description;
        itinerary.theme = theme;
        itinerary.siteSequence = new ArrayList<>();
        return itinerary;
    }

    /**
     * Add site to itinerary.
     */
    public void addSite(UUID siteId, Integer order) {
        if (siteId == null) {
            throw new ValidationException("Site ID is required");
        }
        if (this.siteSequence == null) {
            this.siteSequence = new ArrayList<>();
        }
        // Ensure max 50 photos + 10 videos per site constraint applies
        if (this.siteSequence.size() >= 100) {
            throw new ValidationException("Maximum sites per itinerary exceeded");
        }
        this.siteSequence.add(siteId);
    }

    /**
     * Remove site from itinerary.
     */
    public void removeSite(UUID siteId) {
        if (this.siteSequence != null) {
            this.siteSequence.remove(siteId);
        }
    }

    /**
     * Get site count.
     */
    public Integer getSiteCount() {
        return this.siteSequence != null ? this.siteSequence.size() : 0;
    }
}
