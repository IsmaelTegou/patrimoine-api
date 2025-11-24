package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.models.Guide;
import com.ktiservice.patrimoine.services.GuideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    private final GuideService service;

    public GuideController(GuideService service) {
        this.service = service;
    }

    @PostMapping
    public Guide create(@RequestBody Guide guide) {
        return service.create(guide);
    }

    @GetMapping("/{id}")
    public Guide getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Guide> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
