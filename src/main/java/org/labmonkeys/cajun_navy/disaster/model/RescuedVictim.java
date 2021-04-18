package org.labmonkeys.cajun_navy.disaster.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "rescued_victim")
public class RescuedVictim extends PanacheEntityBase {

    @Id
    @Column(name = "victim_id", nullable = false, updatable = false)
    private String victimId;

    @Column(name = "medical_needed")
    private boolean medicalNeeded;

    @Column(name = "victim_name")
    private String victimName;

    @Column(name = "victim_phone_number")
    private String victimPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "shelter", nullable = false)
    private Shelter shelter;
}
