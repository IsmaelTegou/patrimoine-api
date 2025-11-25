package com.ktiservice.patrimoine.infrastructure.web.dto.reviews;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create review request")
public class CreateReviewRequest {

    @NotNull(message = "Site ID is required")
    @Schema(description = "Heritage site ID")
    private UUID siteId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Schema(description = "Rating (1-5)", example = "4")
    private Integer rating;

    @Schema(description = "Comment", example = "Amazing site, worth visiting!")
    private String comment;
}
