package com.ktiservice.patrimoine.entities;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name="visitHistory")
@Entity
public class VisitHistoryJpaEntity {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_history")
    private Long history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_tourist")
    private Long tourist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_site")
    private Long site;

    @CreationTimeStamp
    @Column(name="date_visite", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime dateVisite;
    
    @Column(name="duree_visite", nullable=false)
    private Integer dureeVisite;

    @Column(name="source_Acces", nullable=false)
    private String sourceAcces;
}
