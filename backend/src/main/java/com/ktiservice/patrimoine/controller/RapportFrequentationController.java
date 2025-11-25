package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.models.RapportFrequentation;
import com.ktiservice.patrimoine.services.RapportFrequentationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rapport-frequentation")
public class RapportFrequentationController {

    private final RapportFrequentationService service;

    public RapportFrequentationController(RapportFrequentationService service) {
        this.service = service;
    }

    @PostMapping
    public RapportFrequentation create(@RequestBody RapportFrequentation rapport) {
        return service.create(rapport);
    }

    @GetMapping("/{id}")
    public RapportFrequentation getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<RapportFrequentation> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
