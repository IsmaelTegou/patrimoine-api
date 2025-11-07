package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotation.GenericGenerator;
import org.hibernate.annotation.Type;

import java.util.UUID;
import java.util.Map;

@Entity
@Data
@AllArgsContructor
@NoArgsConstructor
@Table(name="RapportFrequentation")
@EqualsAndHashCode(callSuper = true)

public class RapportFrequentationJpaEntity extends ReviewJpaEntity {
    
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @Column(name="nombre_Visite_Total", nullable=false)
    private Integer nombre_Visite_Total;

    @Type(org.hibernate.Type.TextType.Class)
    @Column(name="visiteParSite", nullable=false, columnDefinition="TEXT")
    private String visiteParSite;

    @Type(org.hibernate.Type.TextType.Class)
    @Column(name="visiteParJour", nullable=false, columnDefinition="TEXT")
    private String visiteParJour;
}
