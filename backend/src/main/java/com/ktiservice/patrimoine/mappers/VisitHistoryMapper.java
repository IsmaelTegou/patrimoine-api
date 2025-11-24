package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.dto.visitHistory.CreateVisitHistoryRequest;
import com.ktiservice.patrimoine.dto.visitHistory.VisitHistoryResponse;
import com.ktiservice.patrimoine.entities.VisitHistoryJpaEntity;
import com.ktiservice.patrimoine.entities.TouristJpaEntity;
import com.ktiservice.patrimoine.entities.HeritageNetworkJpaEntity;
import com.ktiservice.patrimoine.models.VisitHistory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VisitHistoryMapper {

    // ----- CreateRequest → Domain -----
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "siteId", source = "siteId")
    @Mapping(target = "visitDuration", source = "visitDuration")
    @Mapping(target = "accessSource", source = "accessSource")
    VisitHistory fromCreateRequest(CreateVisitHistoryRequest request);

    // ----- Domain → Response -----
    VisitHistoryResponse toResponse(VisitHistory model);

    // ----- JPA → Domain -----
    @Mapping(target = "userId", source = "tourist.id")
    @Mapping(target = "siteId", source = "site.id")
    @Mapping(target = "visitDuration", source = "visitDuration")
    @Mapping(target = "accessSource", source = "accessSource")
    VisitHistory toDomainEntity(VisitHistoryJpaEntity jpaEntity);

    // ----- Domain → JPA -----
    @Mapping(target = "tourist", expression = "java(new TouristJpaEntity(model.getUserId()))")
    @Mapping(target = "site", expression = "java(new HeritageNetworkJpaEntity(model.getSiteId()))")
    VisitHistoryJpaEntity toJpaEntity(VisitHistory model);
}
