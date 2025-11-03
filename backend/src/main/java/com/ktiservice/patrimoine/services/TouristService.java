package com.ktiservice.patrimoine.services;

import com.ktiservice.patrimoine.entities.TouristJpaEntity;
import com.ktiservice.patrimoine.models.Tourist;
import com.ktiservice.patrimoine.mappers.TouristMapper;
import com.ktiservice.patrimoine.repository.tourist.TouristRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TouristService {

    private final TouristRepository touristRepository;
    private final TouristMapper touristMapper;

    public TouristService(TouristRepository touristRepository, TouristMapper touristMapper) {
        this.touristRepository = touristRepository;
        this.touristMapper = touristMapper;
    }

    public Tourist createTourist(UUID userId) {
        Tourist tourist = new Tourist(
                UUID.randomUUID(),
                userId,
                LocalDateTime.now(),
                true
        );
        TouristJpaEntity entity = touristMapper.toEntity(tourist);
        touristRepository.save(entity);
        return tourist;
    }

    public List<Tourist> findAllTourist() {
        return touristRepository.findAll()
                .stream()
                .map(touristMapper::toModel)
                .toList();
    }

    public Tourist findTouristById(UUID id) {
        return touristRepository.findById(id)
                .map(touristMapper::toModel)
                .orElse(null);
    }

    /** Désactiver un touriste */
    public Tourist deactivateTourist(UUID id) {
        var entity = touristRepository.findById(id).orElseThrow();
        entity.setActive(false);
        touristRepository.save(entity);
        return touristMapper.toModel(entity);
    }
}
