package com.ktiservice.patrimoine.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT configuration properties binding.
 */
@Component
@ConfigurationProperties(prefix = "app.jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtProperties {
    private String secretKey;
    private long expirationTime;
    private long refreshTokenExpirationTime;
    private String algorithm;
}
