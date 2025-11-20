package com.ktiservice.patrimoine.models;


import java.util.UUID;
import java.time.LocalDateTime;

public class Tourist extends BaseEntity{
    private UUID userId;
    private LocalDateTime dateInscription;
    private boolean active;
    
    
     public Tourist(UUID id, UUID userId, LocalDateTime dateInscription, boolean active) {
        super();
        this.userId = userId;
        this.dateInscription = dateInscription;
        this.active = active;
    }

    public Tourist(UUID userId, LocalDateTime dateInscription, boolean active) {
        this.userId = userId;
        this.dateInscription = dateInscription;
        this.active = active;
    }

     // --- Getters ---
      public UUID getUserId() { return userId; }
      public void setUserId(UUID userId) { this.userId = userId; }

      public LocalDateTime getDateInscription() { return dateInscription; }
      public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }

      public boolean isActive() { return active; }
      public void setActive(boolean active) { this.active = active; }

     // --- Business Logic ---
     public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}
