package com.ktiservice.patrimoine.infrastructure.persistence.repositories;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.ReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for Review.
 */
@Repository
public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, UUID> {

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.deletedAt IS NULL AND r.siteId = :siteId")
    Page<ReviewJpaEntity> findByHeritageSiteId(@Param("siteId") UUID siteId, Pageable pageable);

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.deletedAt IS NULL AND r.siteId = :siteId AND r.isApproved = true")
    Page<ReviewJpaEntity> findApprovedByHeritageSiteId(@Param("siteId") UUID siteId, Pageable pageable);

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.deletedAt IS NULL AND r.isApproved = false ORDER BY r.createdAt ASC")
    Page<ReviewJpaEntity> findPendingReviews(Pageable pageable);

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.deletedAt IS NULL AND r.userId = :userId")
    Page<ReviewJpaEntity> findByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.deletedAt IS NULL AND r.siteId = :siteId AND r.rating = :rating AND r.isApproved = true")
    Page<ReviewJpaEntity> findByHeritageSiteIdAndRating(@Param("siteId") UUID siteId, @Param("rating") Integer rating, Pageable pageable);
}
