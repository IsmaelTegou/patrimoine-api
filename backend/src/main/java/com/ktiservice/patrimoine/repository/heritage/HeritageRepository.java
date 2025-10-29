package com.ktiservice.patrimoine.repository.heritage;

import com.ktiservice.patrimoine.models.HeritageNetwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Heritage repository.
 */
public interface HeritageRepository {
    HeritageNetwork save(HeritageNetwork heritageNetwork);
    Optional<HeritageNetwork> findById(UUID id);
    HeritageNetwork update(HeritageNetwork heritageNetwork);
    void softDelete(UUID id);
    Page<HeritageNetwork> searchByFilters(String province, String heritageType, String searchText, Pageable pageable);
    Page<HeritageNetwork> findTopRatedSites(Pageable pageable);
    Page<HeritageNetwork> findByProvince(String province, Pageable pageable);
}
