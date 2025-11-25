package com.ktiservice.patrimoine.repository.gestionnaireSite;

import com.ktiservice.patrimoine.entities.GestionnaireSiteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GestionnaireSiteJpaRepository extends JpaRepository<GestionnaireSiteJpaEntity, UUID> {
}
