package com.ktiservice.patrimoine.dto.visitHistory;

import lombok.Data;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
public class CreateVisitHistoryRequest {
    
    @NotNull(message = "User ID is required")
    private UUID userId;
    
    @NotNull(message = "Site ID is required")
    private UUID siteId;
    
    @Positive(message = "Visit duration must be positive")
    private Integer visitDuration; // in minutes
    
    @Size(max = 50, message = "Access source must not exceed 50 characters")
    private String accessSource;
}