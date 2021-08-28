package org.labmonkeys.cajun_navy.disaster.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DisasterDTO {
    
    private UUID disasterId;
    private String disasterName;
    private String description;
    private boolean active;
    private List<InclusionZoneDTO> inclusionZones;
    private List<ShelterDTO> shelters;
}
