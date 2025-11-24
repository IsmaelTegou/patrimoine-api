package com.ktiservice.patrimoine.repository.gestionnaireSite;

import com.ktiservice.patrimoine.entities.GestionnaireSiteJpaEntity;
import com.ktiservice.patrimoine.exceptions.ResourceNotFoundException;
import com.ktiservice.patrimoine.mappers.GestionnaireSiteMapper;
import com.ktiservice.patrimoine.models.GestionnaireSite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class GestionnaireSiteRepositoryAdapter implements GestionnaireSiteRepository {

    private final GestionnaireSiteJpaRepository jpaRepository;
    private final GestionnaireSiteMapper mapper;

    public GestionnaireSiteRepositoryAdapter(GestionnaireSiteJpaRepository jpaRepository,
                                             GestionnaireSiteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public GestionnaireSite save(GestionnaireSite gs) {
        GestionnaireSiteJpaEntity entity = mapper.toJpaEntity(gs);
        GestionnaireSiteJpaEntity saved = jpaRepository.save(entity);
        log.debug("GestionnaireSite saved: {}", saved.getId());
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<GestionnaireSite> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomainEntity);
    }

    @Override
    public List<GestionnaireSite> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    public GestionnaireSite update(GestionnaireSite gs) {
        if (!jpaRepository.existsById(gs.getId())) {
            throw new ResourceNotFoundException("GestionnaireSite", gs.getId().toString());
        }
        GestionnaireSiteJpaEntity updated = jpaRepository.save(mapper.toJpaEntity(gs));
        log.debug("GestionnaireSite updated: {}", gs.getId());
        return mapper.toDomainEntity(updated);
    }

    @Override
    public void deleteById(UUID id) {
        if (!jpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("GestionnaireSite", id.toString());
        }
        jpaRepository.deleteById(id);
        log.debug("GestionnaireSite deleted: {}", id);
    }
}
