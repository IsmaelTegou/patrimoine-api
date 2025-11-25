package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.VisitHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Visit History repository.
 */
public interface VisitHistoryRepository {
    VisitHistory save(VisitHistory visitHistory);
    Optional<VisitHistory> findById(UUID id);
    Page<VisitHistory> findByUserId(UUID userId, Pageable pageable);
    Page<VisitHistory> findBySiteId(UUID siteId, Pageable pageable);
    Page<VisitHistory> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
