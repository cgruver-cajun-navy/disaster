package org.labmonkeys.cajun_navy.disaster.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class RescuedVictimDTO {
    private UUID victimId;
    private UUID shelterId;
    private boolean medicalNeeded;
    private String victimName;
    private String victimPhoneNumber;
}
