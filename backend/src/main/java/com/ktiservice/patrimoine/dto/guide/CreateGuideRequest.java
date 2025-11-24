package com.ktiservice.patrimoine.dto.guide;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGuideRequest {
    
    @NotBlank(message = "{guide.specialty.required}")
    @Size(min = 2, max = 100, message = "{guide.specialty.size}")
    private String specialite;
    
    @NotNull(message = "{guide.experience.required}")
    @Range(min = 0, max = 60, message = "{guide.experience.range}")
    private Integer experience;
}