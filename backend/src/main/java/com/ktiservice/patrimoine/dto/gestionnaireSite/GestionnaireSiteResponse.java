package com.ktiservice.patrimoine.dto.gestionnaireSite;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "Gestionnaire site assignment response DTO")
public class GestionnaireSiteResponse {

    @Schema(
        description = "Unique identifier of the gestionnaire-site assignment",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
        description = "Date and time when the gestionnaire was assigned to the site",
        example = "2024-01-15T10:30:00",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dateAssignation;
}