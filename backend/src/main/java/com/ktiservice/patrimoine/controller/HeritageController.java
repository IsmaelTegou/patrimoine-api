package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.entities.UserJpaEntity;
import com.ktiservice.patrimoine.services.HeritageApplicationService;
import com.ktiservice.patrimoine.dto.common.ApiResponse;
import com.ktiservice.patrimoine.dto.common.PagedResponse;
import com.ktiservice.patrimoine.dto.common.PaginationRequest;
import com.ktiservice.patrimoine.dto.heritage.CreateHeritageRequest;
import com.ktiservice.patrimoine.dto.heritage.HeritageResponse;
import com.ktiservice.patrimoine.utils.PaginationUtil;
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
 * REST Controller for Heritage Site operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/heritage")
@Tag(name = "Heritage Sites", description = "Heritage site management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class HeritageController {

    private final HeritageApplicationService heritageService;
    private final PaginationUtil paginationUtil;

    public HeritageController(HeritageApplicationService heritageService, PaginationUtil paginationUtil) {
        this.heritageService = heritageService;
        this.paginationUtil = paginationUtil;
    }

    /**
     * Create a new heritage site.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create heritage site", description = "Create a new heritage site (Admin only)")
    public ResponseEntity<ApiResponse<HeritageResponse>> createHeritageSite(
            @Valid @RequestBody CreateHeritageRequest request,
            Authentication authentication) {
        log.info("Creating heritage site: {}", request.getSiteName());

        HeritageResponse response = heritageService.createHeritageNetwork(
                request,
                ((UserJpaEntity) authentication.getPrincipal()).getId(),
                authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    /**
     * Get heritage site by ID.
     */
    @GetMapping("/{siteId}")
    @Operation(summary = "Get heritage site", description = "Retrieve details of a specific heritage site")
    public ResponseEntity<ApiResponse<HeritageResponse>> getHeritageNetworkById(
            @PathVariable UUID siteId) {
        log.info("Fetching heritage site: {}", siteId);

        HeritageResponse response = heritageService.getHeritageNetworkById(siteId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Search heritage sites with filters.
     */
    @GetMapping("/search")
    @Operation(summary = "Search heritage sites", description = "Search sites with filters (province, type, keyword)")
    public ResponseEntity<PagedResponse<HeritageResponse>> searchHeritageSites(
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String heritageType,
            @RequestParam(required = false) String search,
            @Valid PaginationRequest paginationRequest) {

        Pageable pageable = paginationUtil.createPageable(
                paginationRequest.getPageNumberOrDefault(),
                paginationRequest.getPageSizeOrDefault(),
                paginationRequest.getSortByOrDefault(),
                paginationRequest.getSortDirectionOrDefault());

        Page<HeritageResponse> page = heritageService.searchHeritageSites(province, heritageType, search, pageable);

        PagedResponse<HeritageResponse> response = PagedResponse.<HeritageResponse>builder()
                .status(200)
                .message("Heritage sites retrieved successfully")
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
     * Update heritage site.
     */
    @PutMapping("/{siteId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @Operation(summary = "Update heritage site", description = "Update heritage site information")
    public ResponseEntity<ApiResponse<HeritageResponse>> updateHeritageSite(
            @PathVariable UUID siteId,
            @Valid @RequestBody CreateHeritageRequest request,
            Authentication authentication) {

        HeritageResponse response = heritageService.updateHeritageNetwork(
                siteId,
                request,
                ((UserJpaEntity) authentication.getPrincipal()).getId(),
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Delete heritage site.
     */
    @DeleteMapping("/{siteId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete heritage site", description = "Soft delete a heritage site")
    public ResponseEntity<ApiResponse<String>> deleteHeritageSite(
            @PathVariable UUID siteId,
            Authentication authentication) {

        heritageService.deleteHeritageNetwork(
                siteId,
                ((UserJpaEntity) authentication.getPrincipal()).getId(),
                authentication.getName());

        return ResponseEntity.ok(ApiResponse.success("Heritage site deleted successfully"));
    }

    /**
     * Get top rated sites.
     */
    @GetMapping("/top-rated")
    @Operation(summary = "Get top rated sites", description = "Retrieve the most highly rated heritage sites")
    public ResponseEntity<PagedResponse<HeritageResponse>> getTopRatedSites(
            @Valid PaginationRequest paginationRequest) {

        Pageable pageable = paginationUtil.createPageable(
                paginationRequest.getPageNumberOrDefault(),
                paginationRequest.getPageSizeOrDefault());

        Page<HeritageResponse> page = heritageService.getTopRatedSites(pageable);

        PagedResponse<HeritageResponse> response = PagedResponse.<HeritageResponse>builder()
                .status(200)
                .message("Top rated sites retrieved successfully")
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
}
