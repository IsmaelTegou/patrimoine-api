package com.ktiservice.patrimoine.dto.reviews;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Review response")
public class ReviewResponse {

    @Schema(description = "Review ID")
    private UUID id;

    @Schema(description = "Site ID")
    private UUID siteId;

    @Schema(description = "User ID")
    private UUID userId;

    @Schema(description = "Rating (1-5)")
    private Integer rating;

    @Schema(description = "Comment")
    private String comment;

    @Schema(description = "Is approved")
    private boolean isApproved;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}