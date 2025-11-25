package com.ktiservice.patrimoine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main entry point for the Heritage Management Platform application.
 * Configures Spring Boot, enables caching, async processing, and OpenAPI/Swagger documentation.
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@OpenAPIDefinition(
        info = @Info(
                title = "Heritage Management Platform API",
                version = "1.0.0",
                description = "API for managing and promoting heritage tourism in Grand North Cameroon",
                contact = @Contact(
                        name = "KTI Service",
                        email = "support@ktiservice.com"
                ),
                license = @License(
                        name = "Proprietary",
                        url = "https://ktiservice.com/license"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Bearer token authentication",
        in = SecuritySchemeIn.HEADER
)
public class PatrimoineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrimoineApplication.class, args);
	}

}
