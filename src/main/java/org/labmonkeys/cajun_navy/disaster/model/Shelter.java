package org.labmonkeys.cajun_navy.disaster.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(name = "shelter_name")
    private String shelterName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "capacity")
    private long capacity;

    @Column(name = "number_rescued")
    private long numRescued;

    @ManyToOne
    @JoinColumn(name = "disaster", nullable = false)
    private Disaster disaster;
}
