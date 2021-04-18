package org.labmonkeys.cajun_navy.disaster.colaborators.responder;

import lombok.Data;

@Data
public class ResponderQueryDTO {
    String disasterId;
    Integer limit;
    Integer offset;
}
