package org.labmonkeys.cajun_navy.disaster.colaborators.responder;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ResponderDTO {
    private String responderId;
    private String name;
    private String phoneNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer boatCapacity;
    private Boolean medicalKit;
    private Boolean available;
    private Boolean person;
    private Boolean enrolled;
}
