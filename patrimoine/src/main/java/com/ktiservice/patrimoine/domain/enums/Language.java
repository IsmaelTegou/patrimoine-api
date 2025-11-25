package com.ktiservice.patrimoine.domain.enums;

import lombok.Getter;

/**
 * Supported languages in the platform.
 */
@Getter
public enum Language {
    FRENCH("Français", "fr"),
    ENGLISH("English", "en"),
    FULANI("Fulbé", "ff");  // Future support

    private final String displayName;
    private final String code;

    Language(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

}
