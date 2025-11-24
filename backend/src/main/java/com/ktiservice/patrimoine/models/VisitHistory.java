package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;

import java.util.UUID;

/**
 * Visit history domain entity.
 * Tracks when users visit heritage sites.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class VisitHistory extends BaseEntity {

    private UUID userId;
    private UUID siteId;
    private Integer visitDuration;
    private String accessSource;

    // Constructeur à 4 arguments pour MapStruct et usage direct
    public VisitHistory(UUID userId, UUID siteId, Integer visitDuration, String accessSource) {
        if (userId == null) {
            throw new ValidationException("User ID is required");
        }
        if (siteId == null) {
            throw new ValidationException("Site ID is required");
        }
        this.userId = userId;
        this.siteId = siteId;
        this.visitDuration = visitDuration;
        this.accessSource = accessSource != null ? accessSource : "WEB";
    }

    // Factory pour créer un VisitHistory avec valeurs par défaut
    public static VisitHistory create(UUID userId, UUID siteId) {
        return new VisitHistory(userId, siteId, null, "WEB");
    }

    // Setter sécurisé pour la durée de visite
    public void setVisitDuration(Integer minutes) {
        if (minutes != null && minutes > 0) {
            this.visitDuration = minutes;
        }
    }
}
