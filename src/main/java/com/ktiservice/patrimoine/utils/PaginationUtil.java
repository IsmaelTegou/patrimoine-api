package com.ktiservice.patrimoine.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Utility class for pagination and sorting operations.
 * Provides safe pagination with max page size validation.
 */
@Component
public class PaginationUtil {

    @Value("${app.pagination.default-page-size:20}")
    private int defaultPageSize;

    @Value("${app.pagination.max-page-size:100}")
    private int maxPageSize;

    @Value("${app.pagination.default-sort-direction:DESC}")
    private String defaultSortDirection;

    @Value("${app.pagination.default-sort-by:createdAt}")
    private String defaultSortBy;

    /**
     * Create a Pageable object with safe defaults and validation.
     *
     * @param pageNumber The page number (0-indexed)
     * @param pageSize The number of items per page
     * @param sortBy The field to sort by
     * @param sortDirection The sort direction (ASC or DESC)
     * @return Pageable object
     */
    public Pageable createPageable(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        // Validate page number
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        // Validate and limit page size
        if (pageSize <= 0) {
            pageSize = defaultPageSize;
        }
        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }

        // Validate sort field
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = defaultSortBy;
        }

        // Validate sort direction
        Sort.Direction direction;
        try {
            direction = Sort.Direction.valueOf(sortDirection.toUpperCase());
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.valueOf(defaultSortDirection);
        }

        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    /**
     * Create a Pageable with default sorting.
     */
    public Pageable createPageable(int pageNumber, int pageSize) {
        return createPageable(pageNumber, pageSize, defaultSortBy, defaultSortDirection);
    }

    /**
     * Create a Pageable with just page number (uses default size).
     */
    public Pageable createPageable(int pageNumber) {
        return createPageable(pageNumber, defaultPageSize, defaultSortBy, defaultSortDirection);
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }
}
