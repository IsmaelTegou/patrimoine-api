package com.ktiservice.patrimoine.enums;

/**
 * Types of media files that can be uploaded to sites.
 */
public enum MediaType {
    PHOTO("Photo - Image file (JPG, PNG)"),
    VIDEO("Video - Video file (MP4, AVI)");

    private final String description;

    MediaType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
