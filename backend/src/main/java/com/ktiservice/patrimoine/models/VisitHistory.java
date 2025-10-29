package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Visit history domain entity.
 * Tracks when users visit heritage sites.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VisitHistory extends BaseEntity {

    private UUID userId;
    private UUID siteId;
    private Integer visitDuration;
    private String accessSource;

    /**
     * Factory method to create new VisitHistory.
     */
    public static VisitHistory create(UUID userId, UUID siteId) {
        if (userId == null) {
            throw new ValidationException("User ID is required");
        }
        if (siteId == null) {
            throw new ValidationException("Site ID is required");
        }

        VisitHistory history = new VisitHistory();
        history.userId = userId;
        history.siteId = siteId;
        history.accessSource = "WEB";
        return history;
    }

    /**
     * Set visit duration in minutes.
     */
    public void setVisitDuration(Integer minutes) {
        if (minutes != null && minutes > 0) {
            this.visitDuration = minutes;
        }
    }
}
