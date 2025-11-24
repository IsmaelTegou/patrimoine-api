package com.ktiservice.patrimoine.repository.rapportFrequentation;

import com.ktiservice.patrimoine.entities.RapportFrequentationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RapportFrequentationJpaRepository extends JpaRepository<RapportFrequentationJpaEntity, UUID> {
}
