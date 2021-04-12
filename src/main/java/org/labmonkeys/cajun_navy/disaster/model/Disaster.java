package org.labmonkeys.cajun_navy.disaster.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "disaster")
public class Disaster extends PanacheEntityBase {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long disasterId;

    @Column(name = "disaster_name", nullable = false, updatable = false, unique = true)
    private String disasterName;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "zoom", nullable = false)
    private String zoom;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disaster", cascade = CascadeType.ALL)
    private List<InclusionZone> inclusionZones;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disaster", cascade = CascadeType.ALL)
    private List<Shelter> shelters;
}
