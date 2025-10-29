package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.Review;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.repository.review.ReviewRepository;
import com.ktiservice.patrimoine.repository.heritage.HeritageRepository;
import com.ktiservice.patrimoine.mappers.ReviewMapper;
import com.ktiservice.patrimoine.dto.reviews.CreateReviewRequest;
import com.ktiservice.patrimoine.dto.reviews.ReviewResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Application service for Review operations.
 * Manages reviews and ratings on heritage sites.
 */
@Slf4j
@Service
@Transactional
public class ReviewApplicationService {

    private final ReviewRepository reviewRepository;
    private final HeritageRepository heritageRepository;
    private final ReviewMapper reviewMapper;
    private final AuditService auditService;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewApplicationService(ReviewRepository reviewRepository,
                                    HeritageRepository heritageRepository,
                                    ReviewMapper reviewMapper,
                                    AuditService auditService,
                                    ApplicationEventPublisher eventPublisher) {
        this.reviewRepository = reviewRepository;
        this.heritageRepository = heritageRepository;
        this.reviewMapper = reviewMapper;
        this.auditService = auditService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Create a new review.
     */
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, UUID userId, String userEmail) {
        log.info("Creating review for site: {} by user: {}", request.getSiteId(), userId);

        // Verify site exists
        heritageRepository.findById(request.getSiteId())
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", request.getSiteId().toString()));

        Review review = reviewMapper.fromCreateRequest(request, userId);
        review.setCreatedBy(userEmail);

        Review saved = reviewRepository.save(review);

        auditService.logSimpleAction("CREATE", "Review", saved.getId(), userId, userEmail,
                "Created review on site: " + request.getSiteId());

        log.info("Review created successfully: {}", saved.getId());
        return reviewMapper.toResponse(saved);
    }

    /**
     * Get all reviews for a site.
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsBySite(UUID siteId, boolean onlyApproved, Pageable pageable) {
        log.debug("Fetching reviews for site: {} (approved only: {})", siteId, onlyApproved);

        if (onlyApproved) {
            return reviewRepository.findApprovedByHeritageSiteId(siteId, pageable)
                    .map(reviewMapper::toResponse);
        }
        return reviewRepository.findByHeritageSiteId(siteId, pageable)
                .map(reviewMapper::toResponse);
    }

    /**
     * Get pending reviews for moderation.
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getPendingReviews(Pageable pageable) {
        log.debug("Fetching pending reviews");
        return reviewRepository.findPendingReviews(pageable)
                .map(reviewMapper::toResponse);
    }

    /**
     * Approve a review.
     */
    @Transactional
    public ReviewResponse approveReview(UUID reviewId, UUID approvedBy, String approvedByEmail) {
        log.info("Approving review: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", reviewId.toString()));

        review.approve();
        review.setUpdatedBy(approvedByEmail);

        Review updated = reviewRepository.update(review);

        auditService.logSimpleAction("APPROVE", "Review", reviewId, approvedBy, approvedByEmail,
                "Approved review on site: " + review.getSiteId());

        log.info("Review approved: {}", reviewId);
        return reviewMapper.toResponse(updated);
    }

    /**
     * Reject a review.
     */
    @Transactional
    public ReviewResponse rejectReview(UUID reviewId, UUID rejectedBy, String rejectedByEmail) {
        log.info("Rejecting review: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", reviewId.toString()));

        review.reject();
        review.setUpdatedBy(rejectedByEmail);

        Review updated = reviewRepository.update(review);

        auditService.logSimpleAction("REJECT", "Review", reviewId, rejectedBy, rejectedByEmail,
                "Rejected review on site: " + review.getSiteId());

        log.info("Review rejected: {}", reviewId);
        return reviewMapper.toResponse(updated);
    }

    /**
     * Delete a review.
     */
    @Transactional
    public void deleteReview(UUID reviewId, UUID deletedBy, String deletedByEmail) {
        log.info("Deleting review: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", reviewId.toString()));

        reviewRepository.softDelete(reviewId);

        auditService.logSimpleAction("DELETE", "Review", reviewId, deletedBy, deletedByEmail,
                "Deleted review: " + reviewId);

        log.info("Review deleted: {}", reviewId);
    }

    /**
     * Get user's reviews.
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getUserReviews(UUID userId, Pageable pageable) {
        log.debug("Fetching reviews by user: {}", userId);
        return reviewRepository.findByUserId(userId, pageable)
                .map(reviewMapper::toResponse);
    }

    /**
     * Get reviews by rating.
     */
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsByRating(UUID siteId, Integer rating, Pageable pageable) {
        log.debug("Fetching reviews for site {} with rating: {}", siteId, rating);
        return reviewRepository.findByHeritageSiteIdAndRating(siteId, rating, pageable)
                .map(reviewMapper::toResponse);
    }
}
