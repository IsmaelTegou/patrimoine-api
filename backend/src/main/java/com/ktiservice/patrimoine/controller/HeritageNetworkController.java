package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.dto.heritage.CreateHeritageRequest;
import com.ktiservice.patrimoine.dto.heritage.HeritageResponse;
import com.ktiservice.patrimoine.enums.ConservationState;
import com.ktiservice.patrimoine.services.HeritageApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/heritage-networks")
public class HeritageNetworkController {

    private final HeritageApplicationService service;

    public HeritageNetworkController(HeritageApplicationService service) {
        this.service = service;
    }

    // ------------------- CREATE -------------------
    @PostMapping
    public HeritageResponse create(@RequestBody CreateHeritageRequest request,
                                   @RequestHeader("X-User-Id") UUID userId,
                                   @RequestHeader("X-User-Email") String userEmail) {
        return service.createHeritageNetwork(request, userId, userEmail);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public HeritageResponse getById(@PathVariable UUID id) {
        return service.getHeritageNetworkById(id);
    }

    // ------------------- GET ALL WITH PAGINATION -------------------
    @GetMapping
    public Page<HeritageResponse> getAll(Pageable pageable) {
        return service.searchHeritageSites(null, null, null, pageable);
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public HeritageResponse update(@PathVariable UUID id,
                                   @RequestBody CreateHeritageRequest request,
                                   @RequestHeader("X-User-Id") UUID userId,
                                   @RequestHeader("X-User-Email") String userEmail) {
        return service.updateHeritageNetwork(id, request, userId, userEmail);
    }

    // ------------------- UPDATE CONSERVATION STATE -------------------
    @PatchMapping("/{id}/conservation")
    public HeritageResponse updateConservation(@PathVariable UUID id,
                                               @RequestParam ConservationState newState,
                                               @RequestHeader("X-User-Id") UUID userId,
                                               @RequestHeader("X-User-Email") String userEmail) {
        return service.updateConservationState(id, newState, userId, userEmail);
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id,
                       @RequestHeader("X-User-Id") UUID userId,
                       @RequestHeader("X-User-Email") String userEmail) {
        service.deleteHeritageNetwork(id, userId, userEmail);
    }

    // ------------------- SEARCH -------------------
    @GetMapping("/search")
    public Page<HeritageResponse> search(@RequestParam(required = false) String province,
                                         @RequestParam(required = false) String heritageType,
                                         @RequestParam(required = false) String searchText,
                                         Pageable pageable) {
        return service.searchHeritageSites(province, heritageType, searchText, pageable);
    }

    // ------------------- TOP RATED -------------------
    @GetMapping("/top-rated")
    public Page<HeritageResponse> topRated(Pageable pageable) {
        return service.getTopRatedSites(pageable);
    }
}
