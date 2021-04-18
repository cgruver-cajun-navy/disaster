package org.labmonkeys.cajun_navy.disaster.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ShelterDTO {
    private Long shelterId;
    private String shelterName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private long capacity;
    private List<RescuedVictimDTO> rescuedVictims;
}
