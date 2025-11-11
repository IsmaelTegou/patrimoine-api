package com.ktiservice.patrimoine.entities;

import java.time.LocalDateTime;
import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name="Itinerary")
@Entity

public class ItineraryJpaEntity {
    
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @Column(name="title", nullable= false)
    private String title;

    @Column(name="description", nullable= true)
    private String description;

    @Column(name="time_estimation", nullable= false)
    private Integer time_estimation;

    @Column(name="theme", nullable=true)
    private String theme;

    @CreationTimestamp
    @Column(name="date_creation", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime date_creation;

}
