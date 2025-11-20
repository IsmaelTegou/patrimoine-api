package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="gestionnaireSite")

public class GestionnaireSiteJpaEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
    @Column(name="id", columnDefinition="UUID")
    private UUID id;

   @CreationTimestamp
    @Column(name="dateAssignation", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime dateAssignation;
}
