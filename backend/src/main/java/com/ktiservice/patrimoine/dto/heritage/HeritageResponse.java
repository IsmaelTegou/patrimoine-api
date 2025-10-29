package com.ktiservice.patrimoine.dto.heritage;

import com.ktiservice.patrimoine.enums.ConservationState;
import com.ktiservice.patrimoine.enums.HeritageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Heritage site response")
public class HeritageResponse {

    @Schema(description = "Site ID")
    private UUID id;

    @Schema(description = "Site name")
    private String siteName;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Heritage type")
    private HeritageType heritageType;

    @Schema(description = "Latitude")
    private Double latitude;

    @Schema(description = "Longitude")
    private Double longitude;

    @Schema(description = "Province")
    private String province;

    @Schema(description = "Opening time")
    private LocalTime openingTime;

    @Schema(description = "Closing time")
    private LocalTime closingTime;

    @Schema(description = "Entry fee")
    private BigDecimal entryFee;

    @Schema(description = "Max capacity")
    private Integer maxCapacity;

    @Schema(description = "Conservation state")
    private ConservationState conservationState;

    @Schema(description = "Manager name")
    private String managerContactName;

    @Schema(description = "Manager phone")
    private String managerPhoneNumber;

    @Schema(description = "Average rating (1-5)")
    private Double averageRating;

    @Schema(description = "Total reviews")
    private Integer totalReviews;

    @Schema(description = "Total visits")
    private Integer totalVisits;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}
