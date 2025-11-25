package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.dto.reviews.CreateReviewRequest;
import com.ktiservice.patrimoine.dto.reviews.ReviewResponse;
import com.ktiservice.patrimoine.services.ReviewApplicationService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewApplicationService reviewApplicationService;

    /**
     * Create a new review
     */
    @PostMapping
    public ReviewResponse createReview(
            @RequestBody CreateReviewRequest request,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Email") String userEmail) {

        return reviewApplicationService.createReview(request, userId, userEmail);
    }

    /**
     * Get paginated reviews for a heritage site
     */
    @GetMapping("/site/{siteId}")
    public Page<ReviewResponse> getReviewsBySite(
            @PathVariable UUID siteId,
            @RequestParam(defaultValue = "true") boolean onlyApproved,
            Pageable pageable) {

        return reviewApplicationService.getReviewsBySite(siteId, onlyApproved, pageable);
    }

    /**
     * Get reviews of a specific user
     */
    @GetMapping("/user/{userId}")
    public Page<ReviewResponse> getUserReviews(
            @PathVariable UUID userId,
            Pageable pageable) {

        return reviewApplicationService.getUserReviews(userId, pageable);
    }

    /**
     * Get pending reviews for moderation
     */
    @GetMapping("/pending")
    public Page<ReviewResponse> getPendingReviews(Pageable pageable) {
        return reviewApplicationService.getPendingReviews(pageable);
    }

    /**
     * Approve review
     */
    @PostMapping("/{reviewId}/approve")
    public ReviewResponse approveReview(
            @PathVariable UUID reviewId,
            @RequestHeader("X-Admin-Id") UUID adminId,
            @RequestHeader("X-Admin-Email") String adminEmail) {

        return reviewApplicationService.approveReview(reviewId, adminId, adminEmail);
    }

    /**
     * Reject review
     */
    @PostMapping("/{reviewId}/reject")
    public ReviewResponse rejectReview(
            @PathVariable UUID reviewId,
            @RequestHeader("X-Admin-Id") UUID adminId,
            @RequestHeader("X-Admin-Email") String adminEmail) {

        return reviewApplicationService.rejectReview(reviewId, adminId, adminEmail);
    }

    /**
     * Delete a review (soft delete)
     */
    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable UUID reviewId,
            @RequestHeader("X-Admin-Id") UUID adminId,
            @RequestHeader("X-Admin-Email") String adminEmail) {

        reviewApplicationService.deleteReview(reviewId, adminId, adminEmail);
    }
}
