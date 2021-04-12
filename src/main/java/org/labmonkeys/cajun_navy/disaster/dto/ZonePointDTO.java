package org.labmonkeys.cajun_navy.disaster.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ZonePointDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
