package com.ktiservice.patrimoine.repository.visitHistory;

import com.ktiservice.patrimoine.models.VisitHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitHistoryRepository {

    VisitHistory save(VisitHistory visitHistory);

    Optional<VisitHistory> findById(UUID id);

    List<VisitHistory> findAll();

    VisitHistory update(VisitHistory visitHistory);

    void deleteById(UUID id);
}
