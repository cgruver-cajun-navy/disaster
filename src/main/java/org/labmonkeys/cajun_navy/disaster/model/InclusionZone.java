package org.labmonkeys.cajun_navy.disaster.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "inclusion_zone")
public class InclusionZone extends PanacheEntityBase {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    
    @Column(name = "zone_name")
    private String zoneName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inclusionZone", cascade = CascadeType.ALL)
    private List<ZonePoint> zonePoints;

    @ManyToOne
    @JoinColumn(name = "disaster", nullable = false)
    private Disaster disaster;
}
