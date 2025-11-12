package com.ktiservice.patrimoine.repository.tourist;

import com.ktiservice.patrimoine.entities.TouristJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TouristRepository extends JpaRepository<TouristJpaEntity, UUID> {
}
