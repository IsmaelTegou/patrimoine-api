package com.ktiservice.patrimoine.infrastructure.persistence.repositories;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA Repository for Refresh Tokens.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

    @Query("SELECT CASE WHEN COUNT(rt) > 0 THEN true ELSE false END " +
            "FROM RefreshTokenEntity rt " +
            "WHERE rt.tokenHash = :tokenHash AND rt.revoked = false AND rt.expiresAt > CURRENT_TIMESTAMP")
    boolean existsValidToken(@Param("tokenHash") String tokenHash);

    void deleteByUserId(UUID userId);
}

