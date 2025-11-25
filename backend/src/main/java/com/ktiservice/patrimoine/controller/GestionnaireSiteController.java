package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.models.GestionnaireSite;
import com.ktiservice.patrimoine.services.GestionnaireSiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestionnaire-site")
public class GestionnaireSiteController {

    private final GestionnaireSiteService service;

    public GestionnaireSiteController(GestionnaireSiteService service) {
        this.service = service;
    }

    @PostMapping
    public GestionnaireSite create(@RequestBody GestionnaireSite gs) {
        return service.create(gs);
    }

    @GetMapping("/{id}")
    public GestionnaireSite getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<GestionnaireSite> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
