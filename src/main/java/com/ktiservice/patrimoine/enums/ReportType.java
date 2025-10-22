package com.ktiservice.patrimoine.enums;

/**
 * Types of reports that can be generated.
 */
public enum ReportType {
    VISITATION("Visitation report - site frequency statistics"),
    REVIEWS("Reviews report - average ratings and feedback"),
    USER_ENGAGEMENT("User engagement report - platform activity metrics");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
