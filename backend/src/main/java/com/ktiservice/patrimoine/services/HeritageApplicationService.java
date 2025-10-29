package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.dto.heritage.CreateHeritageRequest;
import com.ktiservice.patrimoine.dto.heritage.HeritageResponse;
import com.ktiservice.patrimoine.mappers.HeritageMapper;
import com.ktiservice.patrimoine.models.HeritageNetwork;
import com.ktiservice.patrimoine.enums.ConservationState;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.repository.heritage.HeritageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Application service for Heritage Network operations.
 * Manages heritage site information, conservation, and statistics.
 */
@Slf4j
@Service
@Transactional
public class HeritageApplicationService {

    private final HeritageRepository heritageRepository;
    private final HeritageMapper heritageMapper;
    private final AuditService auditService;

    public HeritageApplicationService(HeritageRepository heritageRepository,
                                      HeritageMapper heritageMapper,
                                      AuditService auditService) {
        this.heritageRepository = heritageRepository;
        this.heritageMapper = heritageMapper;
        this.auditService = auditService;
    }

    /**
     * Create a new heritage site.
     */
    @Transactional
    @CacheEvict(value = "heritage-sites", allEntries = true)
    public HeritageResponse createHeritageNetwork(CreateHeritageRequest request, UUID createdBy, String createdByEmail) {
        log.info("Creating heritage site: {}", request.getSiteName());

        HeritageNetwork site = heritageMapper.fromCreateRequest(request);
        site.setCreatedBy(createdByEmail);

        HeritageNetwork saved = heritageRepository.save(site);

        auditService.logSimpleAction("CREATE", "HeritageNetwork", saved.getId(), createdBy, createdByEmail,
                "Created heritage site: " + saved.getSiteName());

        log.info("Heritage site created successfully: {}", saved.getId());
        return heritageMapper.toResponse(saved);
    }

    /**
     * Get heritage site by ID.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "heritage-sites", key = "#siteId")
    public HeritageResponse getHeritageNetworkById(UUID siteId) {
        log.debug("Fetching heritage site: {}", siteId);

        HeritageNetwork site = heritageRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", siteId.toString()));

        return heritageMapper.toResponse(site);
    }

    /**
     * Search heritage sites with filters.
     */
    @Transactional(readOnly = true)
    public Page<HeritageResponse> searchHeritageSites(String province, String heritageType,
                                                      String searchText, Pageable pageable) {
        log.debug("Searching heritage sites with filters");

        return heritageRepository.searchByFilters(province, heritageType, searchText, pageable)
                .map(heritageMapper::toResponse);
    }

    /**
     * Update heritage site information.
     */
    @Transactional
    @CacheEvict(value = "heritage-sites", key = "#siteId")
    public HeritageResponse updateHeritageNetwork(UUID siteId, CreateHeritageRequest request,
                                                  UUID updatedBy, String updatedByEmail) {
        log.info("Updating heritage site: {}", siteId);

        HeritageNetwork site = heritageRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", siteId.toString()));

        site.updateInfo(request.getSiteName(), request.getDescription(),
                request.getOpeningTime(), request.getClosingTime(),
                request.getEntryFee(), request.getMaxCapacity());

        site.setUpdatedBy(updatedByEmail);

        HeritageNetwork updated = heritageRepository.update(site);

        auditService.logSimpleAction("UPDATE", "HeritageNetwork", siteId, updatedBy, updatedByEmail,
                "Updated heritage site: " + site.getSiteName());

        log.info("Heritage site updated successfully: {}", siteId);
        return heritageMapper.toResponse(updated);
    }

    /**
     * Update conservation state.
     */
    @Transactional
    @CacheEvict(value = "heritage-sites", key = "#siteId")
    public HeritageResponse updateConservationState(UUID siteId, ConservationState newState,
                                                    UUID updatedBy, String updatedByEmail) {
        log.info("Updating conservation state for site: {}", siteId);

        HeritageNetwork site = heritageRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", siteId.toString()));

        ConservationState oldState = site.getConservationState();
        site.updateConservationState(newState);
        site.setUpdatedBy(updatedByEmail);

        HeritageNetwork updated = heritageRepository.update(site);

        List<String> oldValues = new ArrayList<>();
        oldValues.add(oldState.toString());
        List<String> newValues = new ArrayList<>();
        newValues.add(newState.toString());

        auditService.logAction("UPDATE", "HeritageNetwork", siteId, updatedBy, updatedByEmail,
                oldValues, newValues, "Updated conservation state");

        return heritageMapper.toResponse(updated);
    }

    /**
     * Delete (soft delete) heritage site.
     */
    @Transactional
    @CacheEvict(value = "heritage-sites", key = "#siteId")
    public void deleteHeritageNetwork(UUID siteId, UUID deletedBy, String deletedByEmail) {
        log.info("Deleting heritage site: {}", siteId);

        HeritageNetwork site = heritageRepository.findById(siteId)
                .orElseThrow(() -> new ResourceNotFoundException("HeritageNetwork", siteId.toString()));

        heritageRepository.softDelete(siteId);

        auditService.logSimpleAction("DELETE", "HeritageNetwork", siteId, deletedBy, deletedByEmail,
                "Deleted heritage site: " + site.getSiteName());

        log.info("Heritage site deleted successfully: {}", siteId);
    }

    /**
     * Get top rated sites.
     */
    @Transactional(readOnly = true)
    public Page<HeritageResponse> getTopRatedSites(Pageable pageable) {
        log.debug("Fetching top rated sites");
        return heritageRepository.findTopRatedSites(pageable)
                .map(heritageMapper::toResponse);
    }

    /**
     * Get sites by province.
     */
    @Transactional(readOnly = true)
    public Page<HeritageResponse> getSitesByProvince(String province, Pageable pageable) {
        log.debug("Fetching sites by province: {}", province);
        return heritageRepository.findByProvince(province, pageable)
                .map(heritageMapper::toResponse);
    }
}

