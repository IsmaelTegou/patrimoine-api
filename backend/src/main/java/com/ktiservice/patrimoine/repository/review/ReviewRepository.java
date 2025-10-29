package com.ktiservice.patrimoine.repository.review;

import com.ktiservice.patrimoine.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Review repository.
 */
public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(UUID id);
    Review update(Review review);
    void softDelete(UUID id);
    Page<Review> findByHeritageSiteId(UUID siteId, Pageable pageable);
    Page<Review> findApprovedByHeritageSiteId(UUID siteId, Pageable pageable);
    Page<Review> findPendingReviews(Pageable pageable);
    Page<Review> findByUserId(UUID userId, Pageable pageable);
    Page<Review> findByHeritageSiteIdAndRating(UUID siteId, Integer rating, Pageable pageable);
}
