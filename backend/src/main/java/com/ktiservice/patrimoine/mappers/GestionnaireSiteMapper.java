package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.dto.gestionnaireSite.CreateGestionnaireSiteRequest;
import com.ktiservice.patrimoine.dto.gestionnaireSite.GestionnaireSiteResponse;
import com.ktiservice.patrimoine.entities.GestionnaireSiteJpaEntity;
import com.ktiservice.patrimoine.models.GestionnaireSite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GestionnaireSiteMapper {

    // -- DOMAIN => RESPONSE --
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dateAssignation", source = "dateAssignation")
    GestionnaireSiteResponse toResponse(GestionnaireSite gs);

    // ----------REQUEST => DOMAIN --
    @Mapping(target = "dateAssignation", expression = "java(java.time.LocalDateTime.now())")
    GestionnaireSite fromCreateRequest(CreateGestionnaireSiteRequest request);

    //--- JPA => DOMAIN --
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dateAssignation", source = "dateAssignation")
    GestionnaireSite toDomainEntity(GestionnaireSiteJpaEntity entity);

    // -- DOMAIN => JPA ----  @Mapping(target = "id", source = "id")
    @Mapping(target = "dateAssignation", source = "dateAssignation")
    GestionnaireSiteJpaEntity toJpaEntity(GestionnaireSite gs);
}
