package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.entities.HeritageNetworkJpaEntity;
import com.ktiservice.patrimoine.dto.heritage.CreateHeritageRequest;
import com.ktiservice.patrimoine.dto.heritage.HeritageResponse;
import com.ktiservice.patrimoine.models.HeritageNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HeritageMapper {

    @Mapping(target = "id", ignore = true)
   // @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "averageRating", expression = "java(0.0)")
    @Mapping(target = "totalReviews", expression = "java(0)")
    @Mapping(target = "totalVisits", expression = "java(0)")
    HeritageNetwork fromCreateRequest(CreateHeritageRequest request);

    HeritageResponse toResponse(HeritageNetwork heritageNetwork);

    HeritageNetwork toDomainEntity(HeritageNetworkJpaEntity jpaEntity);

    @Mapping(target = "updatedBy", ignore = true)
    HeritageNetworkJpaEntity toJpaEntity(HeritageNetwork heritageNetwork);
}
