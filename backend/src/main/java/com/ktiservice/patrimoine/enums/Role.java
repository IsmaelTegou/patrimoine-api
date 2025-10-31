package com.ktiservice.patrimoine.enums;

import lombok.Getter;

/**
 * Enumeration of user roles in the heritage management platform.
 */
@Getter
public enum Role {
    VISITOR("Anonymous Visitor - Can only view content"),
    TOURIST("Registered Tourist - Can create reviews and view history"),
    GUIDE("Tourism Guide - Associated to one heritage site"),
    MANAGER("Site Manager - Manages heritage site information"),
    ADMIN("System Administrator - Full platform access");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
