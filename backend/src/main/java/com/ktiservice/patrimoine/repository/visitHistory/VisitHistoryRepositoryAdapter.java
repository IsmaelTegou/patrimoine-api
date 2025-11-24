package com.ktiservice.patrimoine.repository.visitHistory;

import com.ktiservice.patrimoine.entities.HeritageNetworkJpaEntity;
import com.ktiservice.patrimoine.entities.TouristJpaEntity;
import com.ktiservice.patrimoine.entities.VisitHistoryJpaEntity;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.models.VisitHistory;
import com.ktiservice.patrimoine.repository.heritage.HeritageNetworkJpaRepository;
import com.ktiservice.patrimoine.repository.tourist.TouristRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class VisitHistoryRepositoryAdapter implements VisitHistoryRepository {

    private final VisitHistoryJpaRepository visitHistoryJpaRepository;
    private final TouristRepository touristRepository;
    private final HeritageNetworkJpaRepository siteRepository;

    public VisitHistoryRepositoryAdapter(
            VisitHistoryJpaRepository visitHistoryJpaRepository,
            TouristRepository touristRepository,
            HeritageNetworkJpaRepository siteRepository
    ) {
        this.visitHistoryJpaRepository = visitHistoryJpaRepository;
        this.touristRepository = touristRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public VisitHistory save(VisitHistory visitHistory) {

        TouristJpaEntity tourist = touristRepository.findById(visitHistory.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Tourist", visitHistory.getUserId().toString()));

        HeritageNetworkJpaEntity site = siteRepository.findById(visitHistory.getSiteId())
                .orElseThrow(() -> new ResourceNotFoundException("Site", visitHistory.getSiteId().toString()));

        VisitHistoryJpaEntity jpa = VisitHistoryJpaEntity.fromDomainEntity(
                visitHistory,
                tourist,
                site
        );

        VisitHistoryJpaEntity saved = visitHistoryJpaRepository.save(jpa);

        log.debug("VisitHistory saved: {}", saved.getId());

        return saved.toDomainEntity();
    }


    @Override
    public Optional<VisitHistory> findById(UUID id) {
        return visitHistoryJpaRepository.findById(id)
                .map(VisitHistoryJpaEntity::toDomainEntity);
    }

    @Override
    public List<VisitHistory> findAll() {
        return visitHistoryJpaRepository.findAll()
                .stream()
                .map(VisitHistoryJpaEntity::toDomainEntity)
                .toList();
    }

    @Override
    public VisitHistory update(VisitHistory visitHistory) {

        if (!visitHistoryJpaRepository.existsById(visitHistory.getId())) {
            throw new ResourceNotFoundException("VisitHistory", visitHistory.getId().toString());
        }

        TouristJpaEntity tourist = touristRepository.findById(visitHistory.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tourist", visitHistory.getUserId().toString())
                );

        HeritageNetworkJpaEntity site = siteRepository.findById(visitHistory.getSiteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Site", visitHistory.getSiteId().toString())
                );

        VisitHistoryJpaEntity jpa = VisitHistoryJpaEntity.fromDomainEntity(
                visitHistory,
                tourist,
                site
        );

        VisitHistoryJpaEntity updated = visitHistoryJpaRepository.save(jpa);

        log.debug("VisitHistory updated: {}", updated.getId());
        return updated.toDomainEntity();
    }


    @Override
    public void deleteById(UUID id) {
        if (!visitHistoryJpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("VisitHistory", id.toString());
        }

        visitHistoryJpaRepository.deleteById(id);
        log.debug("VisitHistory deleted: {}", id);
    }
}
