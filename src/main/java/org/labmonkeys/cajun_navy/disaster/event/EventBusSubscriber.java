package org.labmonkeys.cajun_navy.disaster.event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.labmonkeys.cajun_navy.disaster.colaborators.incident.IncidentDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.mission.MissionDTO;
import org.labmonkeys.cajun_navy.disaster.colaborators.responder.ResponderDTO;
import org.labmonkeys.cajun_navy.disaster.service.DisasterProcessor;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class EventBusSubscriber {
    
    @Inject
    DisasterProcessor disasterProcessor;

    @ConsumeEvent("assign-incident")
    @Blocking
    public void assignIncident(IncidentDTO incident) {
        disasterProcessor.assignIncident(incident);
    }

    @ConsumeEvent("responder-available")
    @Blocking
    public void responderAvailable(ResponderDTO responder) {
        disasterProcessor.responderAvailable(responder);
    }

    @ConsumeEvent("mission-rejected")
    @Blocking
    public void missionRejected(MissionDTO mission) {
        disasterProcessor.missionRejected(mission);
    }

    
}
