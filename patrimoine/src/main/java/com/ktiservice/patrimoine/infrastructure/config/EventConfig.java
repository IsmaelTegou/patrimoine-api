package com.ktiservice.patrimoine.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration for event-driven architecture.
 * Enables async event listeners and processing.
 */
@Configuration
@EnableAsync
public class EventConfig {
    // Event listeners are automatically discovered via @EventListener
}
