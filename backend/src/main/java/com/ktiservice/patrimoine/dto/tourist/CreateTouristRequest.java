package com.ktiservice.patrimoine.dto.tourist;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;   
import lombok.*;                           
import java.time.LocalTime;                

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create a Tourist")
public class CreateTouristRequest {

    @NotNull
    @Schema(description = "Name of the tourist", example = "John Doe")
    private String name;

    @NotNull
    @Schema(description = "Time of visit", example = "10:30")
    private LocalTime visitTime;
}

