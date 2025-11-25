package com.ktiservice.patrimoine.infrastructure.persistence.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * JPA entity for audit trail.
 * Records all CRUD operations and sensitive actions for compliance and debugging.
 */
@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_audit_logs_entity_id", columnList = "entity_id"),
        @Index(name = "idx_audit_logs_entity_type", columnList = "entity_type"),
        @Index(name = "idx_audit_logs_user_id", columnList = "user_id"),
        @Index(name = "idx_audit_logs_action", columnList = "action"),
        @Index(name = "idx_audit_logs_created_at", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String action;  // CREATE, READ, UPDATE, DELETE, APPROVE, REJECT

    @Column(nullable = false, length = 100)
    private String entityType;  // User, HeritageNetwork, Review, etc.

    @Column(nullable = false, columnDefinition = "UUID")
    private UUID entityId;

    @Column(columnDefinition = "UUID")
    private UUID userId;

    @Column(length = 255)
    private String userEmail;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> oldValues;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> newValues;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String ipAddress;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
