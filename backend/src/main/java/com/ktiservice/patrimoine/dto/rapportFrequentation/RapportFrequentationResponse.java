package com.ktiservice.patrimoine.dto.rapportFrequentation;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Data
@Schema(description = "Attendance report response DTO")
public class RapportFrequentationResponse {

    @Schema(
        description = "Unique identifier of the attendance report",
        example = "123e4567-e89b-12d3-a456-426614174000",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
        description = "Total number of visits across all sites",
        example = "1500",
        minimum = "0",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer nombreVisiteTotal;

    @Schema(
        description = "JSON string containing visits distribution per site",
        example = "{\"site1\": 500, \"site2\": 750, \"site3\": 250}",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String visiteParSite;

    @Schema(
        description = "JSON string containing visits distribution per day",
        example = "{\"2024-01-15\": 120, \"2024-01-16\": 180, \"2024-01-17\": 200}",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private String visiteParJour;
}