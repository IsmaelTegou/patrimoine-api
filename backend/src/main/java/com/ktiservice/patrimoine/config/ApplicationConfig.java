package com.ktiservice.patrimoine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;

/**
 * General application configuration.
 * Enables JPA auditing and provides common beans.
 */
@Configuration
@EnableJpaAuditing
public class ApplicationConfig {

    /**
     * Provide system clock bean for time-based operations.
     */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
