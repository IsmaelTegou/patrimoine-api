package com.ktiservice.patrimoine.entities;

import java.time.LocalDateTime;
import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name= "Tourists")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TouristJpaEntity {

    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(name= "date_inscription", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime dateInscription;

    @Column(name="isActive",nullable= false)
    private boolean isActive;

    
    public boolean isEnabled(){
        return true;
    }

}
