package com.ktiservice.patrimoine.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO configuration for object storage.
 * Initializes MinIO client for handling media uploads.
 */
@Slf4j
@Configuration
public class MinIOConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.use-ssl:false}")
    private boolean useSsl;

    /**
     * Create MinIO client bean.
     */
    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey, secretKey)
                    .build();

            log.info("MinIO client initialized successfully: {}", minioUrl);
            return minioClient;
        } catch (Exception ex) {
            log.error("Failed to initialize MinIO client", ex);
            throw new RuntimeException("MinIO initialization failed", ex);
        }
    }

}
