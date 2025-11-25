package com.ktiservice.patrimoine.application.mappers;

import com.ktiservice.patrimoine.domain.entities.Media;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.MediaJpaEntity;
import com.ktiservice.patrimoine.infrastructure.web.dto.media.MediaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    MediaResponse toResponse(Media media);

    Media toDomainEntity(MediaJpaEntity jpaEntity);

    MediaJpaEntity toJpaEntity(Media media);
}
