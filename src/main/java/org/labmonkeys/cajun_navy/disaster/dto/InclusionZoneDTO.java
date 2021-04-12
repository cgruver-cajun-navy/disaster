package org.labmonkeys.cajun_navy.disaster.dto;

import java.util.List;

import lombok.Data;

@Data
public class InclusionZoneDTO {
    private Long zoneId;
    private String zoneName;
    private List<ZonePointDTO> zonePoints;
}
