package org.labmonkeys.cajun_navy.disaster.colaborators.incident;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PriorityZoneDTO {

    private String zoneId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal radius;
}
