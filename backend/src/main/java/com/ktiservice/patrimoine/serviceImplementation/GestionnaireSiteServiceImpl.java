package com.ktiservice.patrimoine.serviceImplementation;

import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.models.GestionnaireSite;
import com.ktiservice.patrimoine.repository.gestionnaireSite.GestionnaireSiteRepository;
import com.ktiservice.patrimoine.services.GestionnaireSiteService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class GestionnaireSiteServiceImpl implements GestionnaireSiteService {

    private final GestionnaireSiteRepository repository;

     public GestionnaireSiteServiceImpl(GestionnaireSiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public GestionnaireSite create(GestionnaireSite gs) {
        return repository.save(gs);
    }

    @Override
    public GestionnaireSite update(UUID id, GestionnaireSite updated) {
        GestionnaireSite existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GestionnaireSite", id.toString()));

        // Update fields
        existing.setDateAssignation(updated.getDateAssignation());

        return repository.update(existing);
    }

    @Override
    public GestionnaireSite getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GestionnaireSite", id.toString()));
    }

    @Override
    public List<GestionnaireSite> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
