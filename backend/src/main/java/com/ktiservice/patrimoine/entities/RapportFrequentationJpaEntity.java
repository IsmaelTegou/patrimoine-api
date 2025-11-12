package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper = true)

public class RapportFrequentationJpaEntity extends ReviewJpaEntity {
    
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @Column(name="nombre_Visite_Total", nullable=false)
    private Integer nombre_Visite_Total;

    @Lob
    @Column(name="visiteParSite", nullable=false, columnDefinition="TEXT")
    private String visiteParSite;

    @Lob
    @Column(name="visiteParJour", nullable=false)
    private String visiteParJour;
}
