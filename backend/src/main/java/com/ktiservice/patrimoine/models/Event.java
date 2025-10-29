package com.ktiservice.patrimoine.models;

import com.ktiservice.patrimoine.exceptions.ValidationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event domain entity.
 * Represents a cultural event or festival at a heritage site.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseEntity {

    private UUID heritageNetworkId;
    private String eventTitle;
    private String eventDescription;
    private String eventType;
    private String eventLocation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxCapacity;

    /**
     * Factory method to create new Event.
     */
    public static Event create(UUID heritageNetworkId, String eventTitle,
                               LocalDateTime startDate, LocalDateTime endDate) {
        if (heritageNetworkId == null) {
            throw new ValidationException("Heritage network ID is required");
        }
        if (eventTitle == null || eventTitle.isBlank()) {
            throw new ValidationException("Event title is required");
        }
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start and end dates are required");
        }
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("End date must be after start date");
        }

        Event event = new Event();
        event.heritageNetworkId = heritageNetworkId;
        event.eventTitle = eventTitle;
        event.startDate = startDate;
        event.endDate = endDate;
        return event;
    }

    /**
     * Update event information.
     */
    public void updateInfo(String eventTitle, String eventDescription, String eventType,
                           String eventLocation, Integer maxCapacity) {
        if (eventTitle != null && !eventTitle.isBlank()) {
            this.eventTitle = eventTitle;
        }
        if (eventDescription != null) {
            this.eventDescription = eventDescription;
        }
        if (eventType != null) {
            this.eventType = eventType;
        }
        if (eventLocation != null) {
            this.eventLocation = eventLocation;
        }
        if (maxCapacity != null && maxCapacity > 0) {
            this.maxCapacity = maxCapacity;
        }
    }

    /**
     * Check if event is happening now.
     */
    public boolean isHappening() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
}
