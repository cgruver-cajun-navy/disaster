package org.labmonkeys.cajun_navy.disaster.dto;

import lombok.Data;

@Data
public class RescuedVictimDTO {
    private String victimId;
    private boolean medicalNeeded;
    private String victimName;
    private String victimPhoneNumber;
}
