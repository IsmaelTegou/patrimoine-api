package com.ktiservice.patrimoine.domain.ports.persistence;

import com.ktiservice.patrimoine.domain.entities.Report;
import com.ktiservice.patrimoine.domain.enums.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Report repository.
 */
public interface ReportRepository {
    Report save(Report report);
    Optional<Report> findById(UUID id);
    Page<Report> findByReportType(ReportType reportType, Pageable pageable);
    Page<Report> findByGeneratedBy(String generatedBy, Pageable pageable);
    Page<Report> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}

