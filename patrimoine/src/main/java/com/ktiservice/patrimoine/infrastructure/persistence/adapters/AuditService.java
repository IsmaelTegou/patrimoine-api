package com.ktiservice.patrimoine.infrastructure.persistence.adapters;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.AuditLogEntity;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * Service for managing audit logs.
 * Records all critical actions for compliance and debugging.
 */
@Slf4j
@Service
@Transactional
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Log an action with before and after values.
     *
     * @param action Action type (CREATE, UPDATE, DELETE, APPROVE, REJECT)
     * @param entityType Entity class name
     * @param entityId Entity ID
     * @param userId User performing the action
     * @param userEmail User email
     * @param oldValues Previous values
     * @param newValues New values
     * @param description Action description
     */
    public void logAction(String action, String entityType, UUID entityId, UUID userId,
                          String userEmail, Map<String, Object> oldValues,
                          Map<String, Object> newValues, String description) {
        try {
            String ipAddress = getClientIpAddress();

            AuditLogEntity auditLog = AuditLogEntity.builder()
                    .id(UUID.randomUUID())
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .userId(userId)
                    .userEmail(userEmail)
                    .oldValues(oldValues)
                    .newValues(newValues)
                    .description(description)
                    .ipAddress(ipAddress)
                    .build();

            auditLogRepository.save(auditLog);
            log.debug("Audit log saved: {} on {} ({})", action, entityType, entityId);
        } catch (Exception ex) {
            log.error("Failed to save audit log", ex);
        }
    }

    /**
     * Log a simple action without values.
     */
    public void logSimpleAction(String action, String entityType, UUID entityId,
                                UUID userId, String userEmail, String description) {
        logAction(action, entityType, entityId, userId, userEmail, null, null, description);
    }

    /**
     * Get client IP address from request.
     */
    private String getClientIpAddress() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String clientIp = request.getHeader("X-Forwarded-For");
                if (clientIp == null || clientIp.isEmpty()) {
                    clientIp = request.getRemoteAddr();
                }
                return clientIp;
            }
        } catch (Exception ex) {
            log.debug("Could not get client IP address", ex);
        }
        return "UNKNOWN";
    }
}

