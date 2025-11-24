package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.dto.rapportFrequentation.CreateRapportFrequentationRequest;
import com.ktiservice.patrimoine.dto.rapportFrequentation.RapportFrequentationResponse;
import com.ktiservice.patrimoine.entities.RapportFrequentationJpaEntity;
import com.ktiservice.patrimoine.models.RapportFrequentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RapportFrequentationMapper {

    //-- DOMAIN => RESPONSE -----
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreVisiteTotal", source = "nombre_Visite_Total")
    @Mapping(target = "visiteParSite", source = "visiteParSite")
    @Mapping(target = "visiteParJour", source = "visiteParJour")
    RapportFrequentationResponse toResponse(RapportFrequentation rapport);

    // - CREATE REQUEST => DOMAIN -----
    @Mapping(target = "nombre_Visite_Total", source = "nombreVisiteTotal")
    @Mapping(target = "visiteParSite", source = "visiteParSite")
    @Mapping(target = "visiteParJour", source = "visiteParJour")
    RapportFrequentation fromCreateRequest(CreateRapportFrequentationRequest request);

    // ---------- JPA => DOMAIN -----
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre_Visite_Total", source = "nombre_Visite_Total")
    @Mapping(target = "visiteParSite", source = "visiteParSite")
    @Mapping(target = "visiteParJour", source = "visiteParJour")
    RapportFrequentation toDomainEntity(RapportFrequentationJpaEntity entity);

    // ---------- DOMAIN => JPA ------
   @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre_Visite_Total", source = "nombre_Visite_Total")
    @Mapping(target = "visiteParSite", source = "visiteParSite")
    @Mapping(target = "visiteParJour", source = "visiteParJour")
    RapportFrequentationJpaEntity toJpaEntity(RapportFrequentation rapport);
}
