package com.ktiservice.patrimoine.enums;

/**
 * Types of heritage/cultural sites managed by the platform.
 */
public enum HeritageType {
    NATURAL_SITE("Natural site - landscapes, geological formations"),
    NATIONAL_RESERVE("National reserve - protected natural areas"),
    NATIONAL_PARK("National park - large protected areas"),
    CHIEFDOM("Chiefdom - traditional administrative seat"),
    PALACE("Palace - royal residence"),
    HISTORICAL_SITE("Historical site - important historical location"),
    MUSEUM("Museum - cultural and historical collections"),
    RITUAL("Ritual - traditional ceremonies and practices"),
    DANCE("Dance - traditional dance forms"),
    TRADITIONAL_CRAFT("Traditional craft - artisanal practices"),
    FESTIVAL("Festival - cultural events and celebrations"),
    ARCHAEOLOGICAL_SITE("Archaeological site - ancient remains");

    private final String description;

    HeritageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
