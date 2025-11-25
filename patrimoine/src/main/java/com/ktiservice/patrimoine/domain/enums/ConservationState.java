package com.ktiservice.patrimoine.domain.enums;

import lombok.Getter;

/**
 * Conservation state of heritage sites.
 */
@Getter
public enum ConservationState {
    GOOD("Site is in good condition and well maintained"),
    FAIR("Site is in acceptable condition but may need maintenance"),
    DEGRADED("Site requires significant restoration and maintenance");

    private final String description;

    ConservationState(String description) {
        this.description = description;
    }

}
