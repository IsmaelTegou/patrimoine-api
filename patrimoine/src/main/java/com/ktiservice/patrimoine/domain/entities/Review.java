package com.ktiservice.patrimoine.domain.entities;

import com.ktiservice.patrimoine.domain.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Review domain entity.
 * Represents a user's review and rating of a heritage site.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {

    private UUID siteId;
    private UUID userId;
    private Integer rating;
    private String comment;
    private boolean isApproved;

    /**
     * Factory method to create a new Review.
     */
    public static Review create(UUID siteId, UUID userId, Integer rating, String comment) {
        if (siteId == null) {
            throw new ValidationException("Site ID is required");
        }
        if (userId == null) {
            throw new ValidationException("User ID is required");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.siteId = siteId;
        review.userId = userId;
        review.rating = rating;
        review.comment = comment != null ? comment.trim() : "";
        review.isApproved = false;
        return review;
    }

    /**
     * Approve review.
     */
    public void approve() {
        this.isApproved = true;
    }

    /**
     * Reject review.
     */
    public void reject() {
        this.isApproved = false;
    }

    /**
     * Update review.
     */
    public void updateReview(Integer rating, String comment) {
        if (rating != null && rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
        if (comment != null) {
            this.comment = comment.trim();
        }
    }
}
