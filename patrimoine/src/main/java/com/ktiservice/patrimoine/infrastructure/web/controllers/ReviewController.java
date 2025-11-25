package com.ktiservice.patrimoine.infrastructure.web.controllers;

import com.ktiservice.patrimoine.application.services.ReviewApplicationService;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.ApiResponse;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.PagedResponse;
import com.ktiservice.patrimoine.infrastructure.web.dto.common.PaginationRequest;
import com.ktiservice.patrimoine.infrastructure.web.dto.reviews.CreateReviewRequest;
import com.ktiservice.patrimoine.infrastructure.web.dto.reviews.ReviewResponse;
import com.ktiservice.patrimoine.infrastructure.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for Review operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "Review and rating management")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    private final ReviewApplicationService reviewService;
    private final PaginationUtil paginationUtil;

    public ReviewController(ReviewApplicationService reviewService, PaginationUtil paginationUtil) {
        this.reviewService = reviewService;
        this.paginationUtil = paginationUtil;
    }

    /**
     * Create a new review.
     */
    @PostMapping
    @PreAuthorize("hasRole('TOURIST')")
    @Operation(summary = "Create review", description = "Create a new review for a heritage site")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication) {

        ReviewResponse response = reviewService.createReview(
                request,
                UUID.fromString(authentication.getPrincipal().toString()),
                authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    /**
     * Get reviews for a site.
     */
    @GetMapping("/site/{siteId}")
    @Operation(summary = "Get site reviews", description = "Retrieve all approved reviews for a heritage site")
    public ResponseEntity<PagedResponse<ReviewResponse>> getReviewsBySite(
            @PathVariable UUID siteId,
            @Valid PaginationRequest paginationRequest) {

        Pageable pageable = paginationUtil.createPageable(
                paginationRequest.getPageNumberOrDefault(),
                paginationRequest.getPageSizeOrDefault());

        Page<ReviewResponse> page = reviewService.getReviewsBySite(siteId, true, pageable);

        PagedResponse<ReviewResponse> response = PagedResponse.<ReviewResponse>builder()
                .status(200)
                .message("Reviews retrieved successfully")
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Get pending reviews for moderation.
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('SITE_MANAGER') or hasRole('ADMINISTRATOR')")
    @Operation(summary = "Get pending reviews", description = "Retrieve reviews awaiting moderation")
    public ResponseEntity<PagedResponse<ReviewResponse>> getPendingReviews(
            @Valid PaginationRequest paginationRequest) {

        Pageable pageable = paginationUtil.createPageable(
                paginationRequest.getPageNumberOrDefault(),
                paginationRequest.getPageSizeOrDefault());

        Page<ReviewResponse> page = reviewService.getPendingReviews(pageable);

        PagedResponse<ReviewResponse> response = PagedResponse.<ReviewResponse>builder()
                .status(200)
                .message("Pending reviews retrieved successfully")
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Approve a review.
     */
    @PutMapping("/{reviewId}/approve")
    @PreAuthorize("hasRole('SITE_MANAGER') or hasRole('ADMINISTRATOR')")
    @Operation(summary = "Approve review", description = "Approve a pending review")
    public ResponseEntity<ApiResponse<ReviewResponse>> approveReview(
            @PathVariable UUID reviewId,
            Authentication authentication) {

        ReviewResponse response = reviewService.approveReview(
                reviewId,
                UUID.fromString(authentication.getPrincipal().toString()),
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Reject a review.
     */
    @PutMapping("/{reviewId}/reject")
    @PreAuthorize("hasRole('SITE_MANAGER') or hasRole('ADMINISTRATOR')")
    @Operation(summary = "Reject review", description = "Reject a pending review")
    public ResponseEntity<ApiResponse<ReviewResponse>> rejectReview(
            @PathVariable UUID reviewId,
            Authentication authentication) {

        ReviewResponse response = reviewService.rejectReview(
                reviewId,
                UUID.fromString(authentication.getPrincipal().toString()),
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Delete a review.
     */
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(summary = "Delete review", description = "Delete a review")
    public ResponseEntity<ApiResponse<String>> deleteReview(
            @PathVariable UUID reviewId,
            Authentication authentication) {

        reviewService.deleteReview(
                reviewId,
                UUID.fromString(authentication.getPrincipal().toString()),
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully"));
    }
}

