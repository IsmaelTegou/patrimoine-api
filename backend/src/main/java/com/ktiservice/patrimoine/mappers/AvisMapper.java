/*package com.ktiservice.patrimoine.mappers;

import com.ktiservice.patrimoine.entities.AvisJpaEntity;
import com.ktiservice.patrimoine.models.Avis;
import org.springframework.stereotype.Component;
import lombok.Builder;

@Builder
@Component
public class AvisMapper {

    public Avis toModel(AvisJpaEntity entity) {
        return new Avis(
                entity.getSite() != null ? entity.getSite().getId() : null,
                entity.getTourist() != null ? entity.getTourist().getId() : null,
                entity.getNote(),
                entity.getComment(),
                entity.getDateCreation(),
                entity.getApprouve()
        );
    }

    public AvisJpaEntity toEntity(Avis model) {
        AvisJpaEntity entity = new AvisJpaEntity();
        entity.setNote(model.getNote());
        entity.setComment(model.getComment());
        entity.setDateCreation(model.getDateCreation());
        entity.setApprouve(model.getApprouve());

        // Mapping des relations PAR ID  nécessite chargement via repository
        // (j'ai fait ça dans le service)
        return entity;
    }
}*/
