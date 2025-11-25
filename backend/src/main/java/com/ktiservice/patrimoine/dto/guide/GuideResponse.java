package com.ktiservice.patrimoine.dto.guide;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Data
@Schema(description = "Guide response DTO")
public class GuideResponse {

    @Schema(
        description = "Unique identifier of the guide",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
        description = "Specialty or area of expertise of the guide",
        example = "Medieval Architecture",
        maxLength = 100
    )
    private String specialite;

    @Schema(
        description = "Years of experience as a guide",
        example = "5",
        minimum = "0",
        maximum = "60"
    )
    private Integer experience;

    @Schema(
        description = "Number of reviews received by the guide",
        example = "25",
        minimum = "0"
    )
    private Integer nombreAvisRecieved;
}