package com.ktiservice.patrimoine.infrastructure.persistence.repositories;

import com.ktiservice.patrimoine.infrastructure.persistence.entities.HeritageNetworkJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for HeritageNetwork.
 */
@Repository
public interface HeritageNetworkJpaRepository extends JpaRepository<HeritageNetworkJpaEntity, UUID> {

    @Query("SELECT h FROM HeritageNetworkJpaEntity h WHERE h.deletedAt IS NULL AND h.province = :province")
    Page<HeritageNetworkJpaEntity> findByProvince(@Param("province") String province, Pageable pageable);

    @Query("SELECT h FROM HeritageNetworkJpaEntity h WHERE h.deletedAt IS NULL ORDER BY h.averageRating DESC")
    Page<HeritageNetworkJpaEntity> findTopRatedSites(Pageable pageable);

    @Query("""
            SELECT h FROM HeritageNetworkJpaEntity h 
            WHERE h.deletedAt IS NULL 
            AND (:province IS NULL OR h.province = :province)
            AND (:heritageType IS NULL OR CAST(h.heritageType as string) = :heritageType)
            AND (:searchText IS NULL OR LOWER(h.siteName) LIKE LOWER(CONCAT('%', :searchText, '%'))
                 OR LOWER(h.description) LIKE LOWER(CONCAT('%', :searchText, '%')))
            """)
    Page<HeritageNetworkJpaEntity> searchByFilters(
            @Param("province") String province,
            @Param("heritageType") String heritageType,
            @Param("searchText") String searchText,
            Pageable pageable);
}
