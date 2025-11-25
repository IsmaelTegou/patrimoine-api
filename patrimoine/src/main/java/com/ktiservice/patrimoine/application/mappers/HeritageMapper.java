package com.ktiservice.patrimoine.application.mappers;

import com.ktiservice.patrimoine.domain.entities.HeritageNetwork;
import com.ktiservice.patrimoine.infrastructure.persistence.entities.HeritageNetworkJpaEntity;
import com.ktiservice.patrimoine.infrastructure.web.dto.heritage.CreateHeritageRequest;
import com.ktiservice.patrimoine.infrastructure.web.dto.heritage.HeritageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HeritageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "averageRating", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "totalReviews", expression = "java(0)")
    @Mapping(target = "totalVisits", expression = "java(0)")
    HeritageNetwork fromCreateRequest(CreateHeritageRequest request);

    HeritageResponse toResponse(HeritageNetwork heritageNetwork);

    HeritageNetwork toDomainEntity(HeritageNetworkJpaEntity jpaEntity);

    HeritageNetworkJpaEntity toJpaEntity(HeritageNetwork heritageNetwork);
}

