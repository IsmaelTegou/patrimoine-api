package com.ktiservice.patrimoine.domain.enums;

import lombok.Getter;

/**
 * Types of reports that can be generated.
 */
@Getter
public enum ReportType {
    VISITATION("Visitation report - site frequency statistics"),
    REVIEWS("Reviews report - average ratings and feedback"),
    USER_ENGAGEMENT("User engagement report - platform activity metrics");

    private final String description;

    ReportType(String description) {
        this.description = description;
    }

}
