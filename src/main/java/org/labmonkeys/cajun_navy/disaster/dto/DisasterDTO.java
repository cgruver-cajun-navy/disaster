package org.labmonkeys.cajun_navy.disaster.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DisasterDTO {
    
    private String disasterId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal zoom;
    private List<InclusionZoneDTO> inclusionZones;
    private List<ShelterDTO> shelters;
}
