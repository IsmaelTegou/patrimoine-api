package com.ktiservice.patrimoine.entities;

import com.ktiservice.patrimoine.models.VisitHistory;
import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    @JoinColumn(name="id_tourist")
    private TouristJpaEntity tourist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_site")
    private HeritageNetworkJpaEntity site;

    @CreationTimestamp
    @Column(name="date_visite", nullable=false, columnDefinition="TIMESTAMP")
    private LocalDateTime dateVisite;
    
    @Column(name="visit_duration", nullable=false)
    private Integer visitDuration;

    @Column(name="access_Source", nullable=false)
    private String accessSource;

    public VisitHistory toDomainEntity() {
        return VisitHistory.builder()
                .userId(this.tourist != null ? this.tourist.getId() : null)
                .siteId(this.site != null ? this.site.getId() : null)
                .visitDuration(this.visitDuration)
                .accessSource(this.accessSource)
                .build();
    }

    public static VisitHistoryJpaEntity fromDomainEntity(
            VisitHistory domain,
            TouristJpaEntity touristEntity,
            HeritageNetworkJpaEntity siteEntity
    ) {
        return VisitHistoryJpaEntity.builder()
                .id(domain.getId())
                .tourist(touristEntity)
                .site(siteEntity)
                .visitDuration(domain.getVisitDuration())
                .accessSource(domain.getAccessSource())
                .build();
    }

}

