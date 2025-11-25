package com.ktiservice.patrimoine.infrastructure.persistence.repositories;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository for audit log persistence.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {

    /**
     * Find all audit logs for a specific entity.
     */
    Page<AuditLogEntity> findByEntityTypeAndEntityId(String entityType, UUID entityId, Pageable pageable);

    /**
     * Find all audit logs by user.
     */
    Page<AuditLogEntity> findByUserId(UUID userId, Pageable pageable);

    /**
     * Find all audit logs by action.
     */
    Page<AuditLogEntity> findByAction(String action, Pageable pageable);

    /**
     * Find audit logs created after a specific date.
     */
    List<AuditLogEntity> findByCreatedAtAfter(LocalDateTime createdAt);
}
