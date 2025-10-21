package com.ktiservice.patrimoine.infrastructure.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Vérification du bon démarrage de l'application.")
public class HealthController {

    @GetMapping
    public Map<String, String> checkHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return response;
    }
}