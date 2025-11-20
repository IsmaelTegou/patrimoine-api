package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.entities.TouristJpaEntity;
import com.ktiservice.patrimoine.models.Tourist;

import org.springframework.stereotype.Component;

import lombok.Builder;

@Builder
@Component
public class TouristMapper {

    public Tourist toModel(TouristJpaEntity entity) {
        return new Tourist(
                entity.getId(),
                entity.getUserId(),
                entity.getDateInscription(),
                entity.isActive()
        );
    }

    public TouristJpaEntity toEntity(Tourist model) {
        return TouristJpaEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .dateInscription(model.getDateInscription())
                .active(model.isActive())
                .build();
    }
}
