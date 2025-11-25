package com.ktiservice.patrimoine.domain.enums;

import lombok.Getter;

/**
 * Enumeration of user roles in the heritage management platform.
 */
@Getter
public enum Role {
    ANONYMOUS_VISITOR("Anonymous Visitor - Can only view content"),
    TOURIST("Registered Tourist - Can create reviews and view history"),
    GUIDE("Tourism Guide - Associated to one heritage site"),
    SITE_MANAGER("Site Manager - Manages heritage site information"),
    ADMINISTRATOR("System Administrator - Full platform access");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
