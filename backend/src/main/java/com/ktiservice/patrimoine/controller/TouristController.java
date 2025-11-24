package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.services.TouristService;
import com.ktiservice.patrimoine.models.Tourist;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @PostMapping
    public Tourist createTourist(@RequestParam UUID id) {
        return touristService.createTourist(id);
    }

    @GetMapping
    public List<Tourist> findAllTourist() {
        return touristService.findAllTourist();
    }

    @GetMapping("/{id}")
    public Tourist findTouristById(@PathVariable UUID id) {
        return touristService.findTouristById(id);
    }

    @PutMapping("/{id}/deactivate")
    public Tourist deactivateTourist(@PathVariable UUID id) {
        return touristService.deactivateTourist(id);
    }
}
