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
@Table(name = "shelter")
public class Shelter extends PanacheEntityBase {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long shelterId;

    @Column(name = "shelter_name", unique = true)
    private String shelterName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "capacity")
    private long capacity;

    @Column(name = "number_rescued")
    private long numRescued;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shelter", cascade = CascadeType.ALL)
    private List<RescuedVictim> victims;

    @ManyToOne
    @JoinColumn(name = "disaster", nullable = false)
    private Disaster disaster;

    public static Shelter updateShelter(Shelter entity) {
        update("capacity = ?1, numRescued = ?2 where shelterId = ?3", entity.getCapacity(), entity.getNumRescued(), entity.getShelterId());
        return findById(entity.shelterId);
    }

    public static Shelter findByName(String name) {
        return find("shelterName", name).firstResult();
    }
}
