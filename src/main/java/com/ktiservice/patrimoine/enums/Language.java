package com.ktiservice.patrimoine.enums;

/**
 * Supported languages in the platform.
 */
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

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }
}
