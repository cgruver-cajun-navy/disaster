package org.labmonkeys.cajun_navy.disaster.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class InclusionZoneDTO {
    private UUID zoneId;
    private String zoneName;
    private List<ZonePointDTO> zonePoints;
}
