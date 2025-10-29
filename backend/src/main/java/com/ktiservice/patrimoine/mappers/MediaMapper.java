package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.models.Media;
import com.ktiservice.patrimoine.entities.MediaJpaEntity;
import com.ktiservice.patrimoine.dto.media.MediaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    MediaResponse toResponse(Media media);

    Media toDomainEntity(MediaJpaEntity jpaEntity);

    MediaJpaEntity toJpaEntity(Media media);
}
