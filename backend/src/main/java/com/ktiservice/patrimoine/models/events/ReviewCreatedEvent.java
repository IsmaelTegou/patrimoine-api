package com.ktiservice.patrimoine.models.events;

import lombok.Getter;

import java.util.UUID;

/**
 * Domain event published when a review is created on a heritage site.
 */
@Getter
public class ReviewCreatedEvent extends DomainEvent {

    private final UUID reviewId;
    private final UUID siteId;
    private final UUID userId;
    private final Integer rating;
    private final String comment;

    public ReviewCreatedEvent(UUID reviewId, UUID siteId, UUID userId, Integer rating, String comment) {
        super("REVIEW_CREATED");
        this.reviewId = reviewId;
        this.siteId = siteId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

}
