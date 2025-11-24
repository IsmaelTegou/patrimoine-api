package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Avis {

    private UUID id;
    private HeritageNetwork site;
    private Tourist tourist;
    private Integer note;        // Rating from 0 to 5
    private String comment;
    private LocalDateTime dateCreation;
    private Boolean approuve;

    private Avis() {
        this.dateCreation = LocalDateTime.now();
        this.approuve = false; // default unapproved
    }

    /**
     * Factory method to create a new Avis with validation.
     */
    public static Avis create(HeritageNetwork site, Tourist tourist, Integer note, String comment) {
        if (site == null) {
            throw new ValidationException("Site is required for an Avis");
        }
        if (tourist == null) {
            throw new ValidationException("Tourist is required for an Avis");
        }
        if (note != null && (note < 0 || note > 5)) {
            throw new ValidationException("Note must be between 0 and 5");
        }

        Avis avis = new Avis();
        avis.id = UUID.randomUUID();
        avis.site = site;
        avis.tourist = tourist;
        avis.note = note;
        avis.comment = comment;
        return avis;
    }

    // -- Business Methods --

    public void approve() {
        this.approuve = true;
    }

    public void disapprove() {
        this.approuve = false;
    }

    public boolean isApproved() {
        return Boolean.TRUE.equals(this.approuve);
    }

    public void updateNote(Integer note) {
        if (note != null && (note < 0 || note > 5)) {
            throw new ValidationException("Note must be between 0 and 5");
        }
        this.note = note;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    // ------------------- Getters -------------------

    public UUID getId() {
        return id;
    }

    public HeritageNetwork getSite() {
        return site;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public Integer getNote() {
        return note;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Boolean getApprouve() {
        return approuve;
    }
}
