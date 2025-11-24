package com.ktiservice.patrimoine.repository.visitHistory;

import com.ktiservice.patrimoine.entities.VisitHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitHistoryJpaRepository extends JpaRepository<VisitHistoryJpaEntity, UUID> {

}
