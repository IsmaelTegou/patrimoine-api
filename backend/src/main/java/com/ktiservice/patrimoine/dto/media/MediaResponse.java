package com.ktiservice.patrimoine.dto.media;

import com.ktiservice.patrimoine.enums.MediaType;
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
@Schema(description = "Media file response")
public class MediaResponse {

    @Schema(description = "Media ID")
    private UUID id;

    @Schema(description = "Heritage site ID")
    private UUID heritageNetworkId;

    @Schema(description = "Media type (PHOTO or VIDEO)")
    private MediaType mediaType;

    @Schema(description = "File name")
    private String fileName;

    @Schema(description = "File size in bytes")
    private Long fileSize;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Presigned access URL (temporary)")
    private String accessUrl;

    @Schema(description = "Version number for versioning")
    private Integer versionNumber;

    @Schema(description = "Upload timestamp")
    private LocalDateTime createdAt;
}

