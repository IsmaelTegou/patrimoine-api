package com.ktiservice.patrimoine.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "kti service",
                        email = "ktiserviceinfos@gmail.com",
                        url = "https://ktiservice.com"
                ),
                description = "OpenAPI documentation for patrimoine",
                title = "OpenAPI Specification - patrimoine",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:5014"
                )
        }
        //security = {@SecurityRequirement(name = "bearerAuth")}
)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class SwaggerConfig {
}