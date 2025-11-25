package com.ktiservice.patrimoine.infrastructure.web.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard pagination request parameters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Pagination request parameters")
public class PaginationRequest {

    @Schema(description = "Page number (0-indexed)", example = "0", defaultValue = "0")
    private Integer pageNumber;

    @Schema(description = "Page size", example = "20", defaultValue = "20")
    private Integer pageSize;

    @Schema(description = "Sort field", example = "createdAt", defaultValue = "createdAt")
    private String sortBy;

    @Schema(description = "Sort direction (ASC or DESC)", example = "DESC", defaultValue = "DESC")
    private String sortDirection;

    /**
     * Get page number with default.
     */
    public int getPageNumberOrDefault() {
        return pageNumber != null && pageNumber >= 0 ? pageNumber : 0;
    }

    /**
     * Get page size with default.
     */
    public int getPageSizeOrDefault() {
        return pageSize != null && pageSize > 0 ? pageSize : 20;
    }

    /**
     * Get sort field with default.
     */
    public String getSortByOrDefault() {
        return sortBy != null && !sortBy.isBlank() ? sortBy : "createdAt";
    }

    /**
     * Get sort direction with default.
     */
    public String getSortDirectionOrDefault() {
        return sortDirection != null && !sortDirection.isBlank() ? sortDirection : "DESC";
    }
}

