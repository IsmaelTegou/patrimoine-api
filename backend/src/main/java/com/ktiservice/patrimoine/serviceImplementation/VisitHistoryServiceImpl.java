package com.ktiservice.patrimoine.serviceImplementation;

import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.exceptions.ValidationException;
import com.ktiservice.patrimoine.models.VisitHistory;
import com.ktiservice.patrimoine.repository.visitHistory.VisitHistoryRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ktiservice.patrimoine.services.VisitHistoryService;

@Slf4j
@Service
public class VisitHistoryServiceImpl implements VisitHistoryService {

    private final VisitHistoryRepository visitHistoryRepository;

    public VisitHistoryServiceImpl(VisitHistoryRepository visitHistoryRepository) {
        this.visitHistoryRepository = visitHistoryRepository;
    }

    @Override
    public VisitHistory create(VisitHistory visitHistory) {

        validate(visitHistory);

        // règle métier : la date de visite est créée automatiquement
        //visitHistory.setDateVisite(LocalDateTime.now());

        VisitHistory saved = visitHistoryRepository.save(visitHistory);

        log.info("VisitHistory created: {}", saved.getId());
        return saved;
    }

    @Override
    public VisitHistory update(UUID id, VisitHistory visitHistory) {

        VisitHistory existing = visitHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("VisitHistory", id.toString()));

        validate(visitHistory);

        // conserver les champs système
        visitHistory.setId(existing.getId());
        visitHistory.setCreatedAt(existing.getCreatedAt());
        visitHistory.setCreatedBy(existing.getCreatedBy());

        VisitHistory updated = visitHistoryRepository.update(visitHistory);

        log.info("VisitHistory updated: {}", updated.getId());
        return updated;
    }

    @Override
    public VisitHistory getById(UUID id) {
        return visitHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("VisitHistory", id.toString()));
    }

    @Override
    public List<VisitHistory> getAll() {
        return visitHistoryRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        if (visitHistoryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("VisitHistory", id.toString());
        }

        visitHistoryRepository.deleteById(id);
        log.info("VisitHistory deleted: {}", id);
    }

    // ---------------- VALIDATION MÉTIER ----------------

    private void validate(VisitHistory visitHistory) {

        if (visitHistory.getUserId() == null) {
            throw new ValidationException("UserId cannot be null");
        }

        if (visitHistory.getSiteId() == null) {
            throw new ValidationException("SiteId cannot be null");
        }

        if (visitHistory.getVisitDuration() != null && visitHistory.getVisitDuration() < 0) {
            throw new ValidationException("Visit duration cannot be negative");
        }

        if (visitHistory.getAccessSource() == null || visitHistory.getAccessSource().isBlank()) {
            throw new ValidationException("AccessSource cannot be empty");
        }
    }
}
