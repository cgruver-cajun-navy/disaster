package org.labmonkeys.cajun_navy.disaster.cassandra.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ZonePoint {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
