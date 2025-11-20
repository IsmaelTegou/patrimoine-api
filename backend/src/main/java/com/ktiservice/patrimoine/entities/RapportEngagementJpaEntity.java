package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rapport_Engagement")
public class RapportEngagementJpaEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
    @Column(name="id", columnDefinition="UUID")
    private UUID id;

    @Column(name="nombreUtilisateursActifs", nullable=false)
    private Integer nombreUtilisateursActifs;

    @Column(name="taux_engagement", nullable=false)
    private Double taux_engagement;

    @Column(name="nombreAvisCreesTotal", nullable=false)
    private Integer nombreAvisCreesTotal;
}
