package com.ktiservice.patrimoine.dto.rapportFrequentation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Data
public class CreateRapportFrequentationRequest {
    
    @NotNull(message = "Total number of visits is required")
    @PositiveOrZero(message = "Total number of visits must be zero or positive")
    private Integer nombreVisiteTotal;
    
    @NotNull(message = "Visits per site data is required")
    @Size(max = 1000, message = "Visits per site data must not exceed 1000 characters")
    @Pattern(regexp = "^[\\w\\s\\-:,{}.\"]*$", message = "Visits per site data contains invalid characters")
    private String visiteParSite;
    
    @NotNull(message = "Visits per day data is required")
    @Size(max = 1000, message = "Visits per day data must not exceed 1000 characters")
    @Pattern(regexp = "^[\\w\\s\\-:,{}.\"]*$", message = "Visits per day data contains invalid characters")
    private String visiteParJour;
}