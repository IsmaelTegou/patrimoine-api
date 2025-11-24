package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.models.VisitHistory;

import java.util.List;
import java.util.UUID;

public interface VisitHistoryService {

    VisitHistory create(VisitHistory visitHistory);

    VisitHistory update(UUID id, VisitHistory visitHistory);

    VisitHistory getById(UUID id);

    List<VisitHistory> getAll();

    void delete(UUID id);
}
