package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="guide")

public class GuideJpaEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
    @Column(name="id", columnDefinition="UUID")
    private UUID id;

    @Column(name="specialite", nullable=false)
    private String specialite;

    @Column(name="experience", nullable=false)
    private Integer experience;

    @Column(name="nombreAvisRecieved", nullable=false)
    private Integer nombreAvisRecieved;
}
