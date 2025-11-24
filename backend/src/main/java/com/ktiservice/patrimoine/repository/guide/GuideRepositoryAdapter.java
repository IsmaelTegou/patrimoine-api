package com.ktiservice.patrimoine.repository.guide;

import com.ktiservice.patrimoine.entities.GuideJpaEntity;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.mappers.GuideMapper;
import com.ktiservice.patrimoine.models.Guide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class GuideRepositoryAdapter implements GuideRepository {

    private final GuideJpaRepository guideJpaRepository;
    private final GuideMapper mapper;

    public GuideRepositoryAdapter(GuideJpaRepository guideJpaRepository, GuideMapper mapper) {
        this.guideJpaRepository = guideJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Guide save(Guide guide) {
        GuideJpaEntity entity = mapper.toJpaEntity(guide);
        GuideJpaEntity saved = guideJpaRepository.save(entity);
        log.debug("Guide saved with id: {}", saved.getId());
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<Guide> findById(UUID id) {
        return guideJpaRepository.findById(id)
                .map(mapper::toDomainEntity);
    }

    @Override
    public List<Guide> findAll() {
        return guideJpaRepository.findAll().stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    public Guide update(Guide guide) {
        if (!guideJpaRepository.existsById(guide.getId())) {
            throw new ResourceNotFoundException("Guide", guide.getId().toString());
        }

        GuideJpaEntity entity = mapper.toJpaEntity(guide);
        GuideJpaEntity updated = guideJpaRepository.save(entity);
        log.debug("Guide updated with id: {}", guide.getId());
        return mapper.toDomainEntity(updated);
    }

    @Override
    public void deleteById(UUID id) {
        if (!guideJpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Guide", id.toString());
        }

        guideJpaRepository.deleteById(id);
        log.debug("Guide deleted with id: {}", id);
    }
}
