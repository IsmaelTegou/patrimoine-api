package com.ktiservice.patrimoine.dto.rapportEngagement;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Data
public class CreateRapportEngagementRequest {
    
    @NotNull(message = "Active users count is required")
    @PositiveOrZero(message = "Active users count must be zero or positive")
    private Integer nombreUtilisateursActifs;
    
    @NotNull(message = "Engagement rate is required")
    @DecimalMin(value = "0.0", message = "Engagement rate must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "Engagement rate must be between 0 and 100")
    private Double tauxEngagement;
    
    @NotNull(message = "Total reviews created count is required")
    @PositiveOrZero(message = "Total reviews created must be zero or positive")
    private Integer nombreAvisCreesTotal;
}
