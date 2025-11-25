package com.ktiservice.patrimoine.infrastructure.persistence.repositories;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA Repository for Password Reset Tokens.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, UUID> {

    Optional<PasswordResetTokenEntity> findByTokenHash(String tokenHash);

    @Query("SELECT CASE WHEN COUNT(prt) > 0 THEN true ELSE false END " +
            "FROM PasswordResetTokenEntity prt " +
            "WHERE prt.tokenHash = :tokenHash AND prt.used = false AND prt.expiresAt > CURRENT_TIMESTAMP")
    boolean existsValidToken(@Param("tokenHash") String tokenHash);

    void deleteByUserId(UUID userId);
}
