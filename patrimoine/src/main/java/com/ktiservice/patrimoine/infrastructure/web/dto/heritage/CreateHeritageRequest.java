package com.ktiservice.patrimoine.infrastructure.web.dto.heritage;

import com.ktiservice.patrimoine.domain.enums.HeritageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create heritage site request")
public class CreateHeritageRequest {

    @NotBlank(message = "Site name is required")
    @Size(min = 3, max = 255, message = "Site name must be 3-255 characters")
    @Schema(description = "Heritage site name", example = "Mount Cameroon")
    private String siteName;

    @Schema(description = "Site description")
    private String description;

    @NotNull(message = "Heritage type is required")
    @Schema(description = "Heritage type", example = "NATURAL_SITE")
    private HeritageType heritageType;

    @NotNull(message = "Latitude is required")
    @DecimalMin("-90") @DecimalMax("90")
    @Schema(description = "Latitude coordinate", example = "4.2033")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin("-180") @DecimalMax("180")
    @Schema(description = "Longitude coordinate", example = "9.7679")
    private Double longitude;

    @Schema(description = "Province/Region", example = "Southwest Region")
    private String province;

    @Schema(description = "Opening time", example = "08:00:00")
    private LocalTime openingTime;

    @Schema(description = "Closing time", example = "18:00:00")
    private LocalTime closingTime;

    @DecimalMin("0")
    @Schema(description = "Entry fee in USD", example = "10.00")
    private BigDecimal entryFee;

    @Min(value = 1, message = "Max capacity must be positive")
    @Schema(description = "Maximum capacity", example = "500")
    private Integer maxCapacity;

    @Schema(description = "Manager contact name", example = "John Doe")
    private String managerContactName;

    @Pattern(regexp = "^\\+?[0-9]{6,20}$", message = "Invalid phone format")
    @Schema(description = "Manager phone number", example = "+237123456789")
    private String managerPhoneNumber;
}