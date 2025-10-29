package com.ktiservice.patrimoine.repository.review;

import com.ktiservice.patrimoine.models.Review;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.repository.review.ReviewRepository;
import com.ktiservice.patrimoine.entities.ReviewJpaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter for ReviewRepository.
 * Converts between domain and JPA entities.
 */
@Slf4j
@Component
public class ReviewRepositoryAdapter implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    public ReviewRepositoryAdapter(ReviewJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Review save(Review review) {
        ReviewJpaEntity jpaEntity = ReviewJpaEntity.fromDomainEntity(review);
        ReviewJpaEntity saved = jpaRepository.save(jpaEntity);
        log.debug("Review saved: {}", saved.getId());
        return saved.toDomainEntity();
    }

    @Override
    public Optional<Review> findById(UUID id) {
        return jpaRepository.findById(id)
                .filter(entity -> entity.getDeletedAt() == null)
                .map(ReviewJpaEntity::toDomainEntity);
    }

    @Override
    public Review update(Review review) {
        if (!jpaRepository.existsById(review.getId())) {
            throw new ResourceNotFoundException("Review", review.getId().toString());
        }
        ReviewJpaEntity jpaEntity = ReviewJpaEntity.fromDomainEntity(review);
        ReviewJpaEntity updated = jpaRepository.save(jpaEntity);
        log.debug("Review updated: {}", updated.getId());
        return updated.toDomainEntity();
    }

    @Override
    public void softDelete(UUID id) {
        ReviewJpaEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", id.toString()));
        entity.setDeletedAt(LocalDateTime.now());
        jpaRepository.save(entity);
        log.debug("Review soft deleted: {}", id);
    }

    @Override
    public Page<Review> findByHeritageSiteId(UUID siteId, Pageable pageable) {
        return jpaRepository.findByHeritageSiteId(siteId, pageable)
                .map(ReviewJpaEntity::toDomainEntity);
    }

    @Override
    public Page<Review> findApprovedByHeritageSiteId(UUID siteId, Pageable pageable) {
        return jpaRepository.findApprovedByHeritageSiteId(siteId, pageable)
                .map(ReviewJpaEntity::toDomainEntity);
    }

    @Override
    public Page<Review> findPendingReviews(Pageable pageable) {
        return jpaRepository.findPendingReviews(pageable)
                .map(ReviewJpaEntity::toDomainEntity);
    }

    @Override
    public Page<Review> findByUserId(UUID userId, Pageable pageable) {
        return jpaRepository.findByUserId(userId, pageable)
                .map(ReviewJpaEntity::toDomainEntity);
    }

    @Override
    public Page<Review> findByHeritageSiteIdAndRating(UUID siteId, Integer rating, Pageable pageable) {
        return jpaRepository.findByHeritageSiteIdAndRating(siteId, rating, pageable)
                .map(ReviewJpaEntity::toDomainEntity);
    }
}
