package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.models.Media;
import com.ktiservice.patrimoine.services.MediaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService service;

    public MediaController(MediaService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public Media getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/site/{siteId}")
    public List<Media> getBySite(@PathVariable UUID siteId) {
        return service.getBySite(siteId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
