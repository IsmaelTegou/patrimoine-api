package com.ktiservice.patrimoine.repository.rapportFrequentation;

import com.ktiservice.patrimoine.entities.RapportFrequentationJpaEntity;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.mappers.RapportFrequentationMapper;
import com.ktiservice.patrimoine.models.RapportFrequentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RapportFrequentationRepositoryAdapter implements RapportFrequentationRepository {

    private final RapportFrequentationJpaRepository jpaRepository;
    private final RapportFrequentationMapper mapper;

    @Override
    public RapportFrequentation save(RapportFrequentation rapport) {
        RapportFrequentationJpaEntity entity = mapper.toJpaEntity(rapport);
        RapportFrequentationJpaEntity saved = jpaRepository.save(entity);
        log.debug("RapportFrequentation saved with id: {}", saved.getId());
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<RapportFrequentation> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomainEntity);
    }

    @Override
    public List<RapportFrequentation> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    public RapportFrequentation update(RapportFrequentation rapport) {
        if (!jpaRepository.existsById(rapport.getId())) {
            throw new ResourceNotFoundException("RapportFrequentation", rapport.getId().toString());
        }
        RapportFrequentationJpaEntity updated = jpaRepository.save(mapper.toJpaEntity(rapport));
        log.debug("RapportFrequentation updated with id: {}", rapport.getId());
        return mapper.toDomainEntity(updated);
    }

    @Override
    public void deleteById(UUID id) {
        if (!jpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("RapportFrequentation", id.toString());
        }
        jpaRepository.deleteById(id);
        log.debug("RapportFrequentation deleted with id: {}", id);
    }
}
