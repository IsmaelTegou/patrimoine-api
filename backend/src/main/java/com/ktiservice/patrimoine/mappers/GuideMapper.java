package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.dto.guide.CreateGuideRequest;
import com.ktiservice.patrimoine.dto.guide.GuideResponse;
import com.ktiservice.patrimoine.entities.GuideJpaEntity;
import com.ktiservice.patrimoine.models.Guide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuideMapper {

    // CREATE REQUEST → DOMAIN
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "nombreAvisRecieved", constant = "0")
    Guide fromCreateRequest(CreateGuideRequest guideRequest);

    // JPA → DOMAIN
    Guide toDomainEntity(GuideJpaEntity entity);

    // DOMAIN → JPA
    GuideJpaEntity toJpaEntity(Guide guide);

    // DOMAIN → RESPONSE
    GuideResponse toResponse(Guide guide);
}
