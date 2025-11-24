package com.ktiservice.patrimoine.dto.rapportEngagement;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Data
@Schema(description = "Engagement report response DTO")
public class RapportEngagementResponse {

    @Schema(
        description = "Unique identifier of the engagement report",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
        description = "Number of active users in the reporting period",
        example = "1250",
        minimum = "0",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer nombreUtilisateursActifs;

    @Schema(
        description = "Engagement rate as a percentage (0-100)",
        example = "15.5",
        minimum = "0.0",
        maximum = "100.0",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Double tauxEngagement;

    @Schema(
        description = "Total number of reviews created by users",
        example = "187",
        minimum = "0",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer nombreAvisCreesTotal;
}