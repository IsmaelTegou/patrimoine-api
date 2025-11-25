package com.ktiservice.patrimoine.infrastructure.persistence.adapters;

import com.ktiservice.patrimoine.domain.entities.HeritageNetwork;
import com.ktiservice.patrimoine.domain.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.domain.ports.persistence.HeritageRepository;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.HeritageNetworkJpaEntity;
import com.ktiservice.patrimoine.infrastructure.persistence.repositories.HeritageNetworkJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter for HeritageRepository.
 * Converts between domain and JPA entities.
 */
@Slf4j
@Component
public class HeritageRepositoryAdapter implements HeritageRepository {

    private final HeritageNetworkJpaRepository jpaRepository;

    public HeritageRepositoryAdapter(HeritageNetworkJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public HeritageNetwork save(HeritageNetwork heritageNetwork) {
        HeritageNetworkJpaEntity jpaEntity = HeritageNetworkJpaEntity.fromDomainEntity(heritageNetwork);
        HeritageNetworkJpaEntity saved = jpaRepository.save(jpaEntity);
        log.debug("Heritage site saved: {}", saved.getId());
        return saved.toDomainEntity();
    }

    @Override
    public Optional<HeritageNetwork> findById(UUID id) {
        return jpaRepository.findById(id)
                .filter(entity -> entity.getDeletedAt() == null)
                .map(HeritageNetworkJpaEntity::toDomainEntity);
    }

    @Override
    public HeritageNetwork update(HeritageNetwork heritageNetwork) {
        if (!jpaRepository.existsById(heritageNetwork.getId())) {
            throw new ResourceNotFoundException("HeritageNetwork", heritageNetwork.getId().toString());
        }
        HeritageNetworkJpaEntity jpaEntity = HeritageNetworkJpaEntity.fromDomainEntity(heritageNetwork);
        HeritageNetworkJpaEntity updated = jpaRepository.save(jpaEntity);
        log.debug("Heritage site updated: {}", updated.getId());
        return updated.toDomainEntity();
    }

    @Override
    public void softDelete(UUID id) {
        HeritageNetworkJpaEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", id.toString()));
        entity.setDeletedAt(LocalDateTime.now());
        jpaRepository.save(entity);
        log.debug("Heritage site soft deleted: {}", id);
    }

    @Override
    public Page<HeritageNetwork> searchByFilters(String province, String heritageType, String searchText, Pageable pageable) {
        return jpaRepository.searchByFilters(province, heritageType, searchText, pageable)
                .map(HeritageNetworkJpaEntity::toDomainEntity);
    }

    @Override
    public Page<HeritageNetwork> findTopRatedSites(Pageable pageable) {
        return jpaRepository.findTopRatedSites(pageable)
                .map(HeritageNetworkJpaEntity::toDomainEntity);
    }

    @Override
    public Page<HeritageNetwork> findByProvince(String province, Pageable pageable) {
        return jpaRepository.findByProvince(province, pageable)
                .map(HeritageNetworkJpaEntity::toDomainEntity);
    }
}

