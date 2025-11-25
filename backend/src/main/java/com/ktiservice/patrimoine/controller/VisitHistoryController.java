package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.models.VisitHistory;
import com.ktiservice.patrimoine.services.VisitHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/visit-history")
public class VisitHistoryController {

    private final VisitHistoryService service;

    public VisitHistoryController(VisitHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public VisitHistory create(@RequestBody VisitHistory visitHistory) {
        return service.create(visitHistory);
    }

    @GetMapping("/{id}")
    public VisitHistory getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    /*@GetMapping("/user/{userId}")
    public List<VisitHistory> getByUser(@PathVariable UUID userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/site/{siteId}")
    public List<VisitHistory> getBySite(@PathVariable UUID siteId) {
        return service.getBySite(siteId);
    } */

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
