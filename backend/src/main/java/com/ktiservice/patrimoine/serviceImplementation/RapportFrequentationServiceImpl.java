package com.ktiservice.patrimoine.serviceImplementation;

import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.models.RapportFrequentation;
import com.ktiservice.patrimoine.repository.rapportFrequentation.RapportFrequentationRepository;
import com.ktiservice.patrimoine.services.RapportFrequentationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.ktiservice.patrimoine.services.RapportFrequentationService;

@Service

public class RapportFrequentationServiceImpl implements RapportFrequentationService {

    private final RapportFrequentationRepository repository;

     public RapportFrequentationServiceImpl(RapportFrequentationRepository repository) {
        this.repository = repository;
    }

    @Override
    public RapportFrequentation create(RapportFrequentation rapport) {
        return repository.save(rapport);
    }

    @Override
    public RapportFrequentation update(UUID id, RapportFrequentation updated) {
        RapportFrequentation existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RapportFrequentation", id.toString()));

        // Update fields
        existing.setNombre_Visite_Total(updated.getNombre_Visite_Total());
        existing.setVisiteParJour(updated.getVisiteParJour());
        existing.setVisiteParSite(updated.getVisiteParSite());

        return repository.update(existing);
    }

    @Override
    public RapportFrequentation getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RapportFrequentation", id.toString()));
    }

    @Override
    public List<RapportFrequentation> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
