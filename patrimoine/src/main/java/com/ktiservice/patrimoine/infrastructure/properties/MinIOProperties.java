package com.ktiservice.patrimoine.infrastructure.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MinIO configuration properties binding.
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinIOProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private boolean useSsl;
    private int connectionTimeout;
    private int socketTimeout;
}
