/*package com.ktiservice.patrimoine.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name="avis")
@AllArgsConstructor
@Entity

public class AvisJpaEntity {
 
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="id_site")
    private HeritageNetworkJpaEntity site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_tourist")
    private TouristJpaEntity tourist;

    @Column(name="note", nullable=true)
    private Integer note;

    @Column(name="comment", nullable=true)
    private String comment;

    @CreationTimestamp
    @Column(name="date_creation", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime date_creation;

    @Column(name="approuve", nullable=true)
    private Boolean approuve;

   

}
*/