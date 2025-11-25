package com.ktiservice.patrimoine.serviceImplementation;

import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.models.Guide;
import com.ktiservice.patrimoine.repository.guide.GuideRepository;
import com.ktiservice.patrimoine.services.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuideServiceImpl implements GuideService {

    private final GuideRepository repository;

    @Override
    public Guide create(Guide guide) {
        return repository.save(guide);
    }

    @Override
    public Guide update(UUID id, Guide updated) {
        Guide existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guide", id.toString()));

        // Update fields
        existing.setSpecialite(updated.getSpecialite());
        existing.setExperience(updated.getExperience());
        existing.setNombreAvisRecieved(updated.getNombreAvisRecieved());

        return repository.update(existing);
    }

    @Override
    public Guide getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guide", id.toString()));
    }

    @Override
    public List<Guide> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
