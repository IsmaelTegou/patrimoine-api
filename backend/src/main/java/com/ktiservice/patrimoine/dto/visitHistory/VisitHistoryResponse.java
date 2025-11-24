package com.ktiservice.patrimoine.dto.visitHistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VisitHistoryResponse {

    @Schema(description = "ID of the user who visited the heritage site", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @NotNull(message = "User ID is required")
    private UUID userId;

    @Schema(description = "ID of the visited heritage site", example = "b425f9fd-22b4-462b-8242-4b82c89e64fb")
    @NotNull(message = "Site ID is required")
    private UUID siteId;

    @Schema(description = "Duration of the visit in minutes", example = "45")
    @NotNull(message = "Visit duration is required")
    @Positive(message = "Visit duration must be positive")
    private Integer visitDuration;

    @Schema(description = "Access source (mobile-app, website, QR code, etc.)", example = "mobile-app")
    @NotBlank(message = "Access source is required")
    @Size(max = 100, message = "Access source cannot exceed 100 characters")
    private String accessSource;

    @Schema(description = "Date and time of the visit", example = "2025-02-01T10:30:00")
    @NotNull(message = "Visit date is required")
    @PastOrPresent(message = "Visit date cannot be in the future")
    private LocalDateTime dateVisite;
}
