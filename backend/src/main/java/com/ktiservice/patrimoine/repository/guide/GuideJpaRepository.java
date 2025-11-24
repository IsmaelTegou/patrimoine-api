package com.ktiservice.patrimoine.repository.guide;

import com.ktiservice.patrimoine.entities.GuideJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data repository.
 */
@Repository
public interface GuideJpaRepository extends JpaRepository<GuideJpaEntity, UUID> {
}
