package org.labmonkeys.cajun_navy.disaster.colaborators.incident;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class IncidentPublisher {

    private final UnicastProcessor<IncidentDTO> incidentProcessor = UnicastProcessor.create();

    public void updateIncidentPriority(IncidentDTO incident) {
        incidentProcessor.onNext(incident);
    }

    @Outgoing("incident-update-priority")
    public Multi<Message<IncidentDTO>> incidentMulti() {
        return incidentProcessor.onItem().transform(this::sendIncident);
    }

    private Message<IncidentDTO> sendIncident(IncidentDTO dto) {
        return KafkaRecord.of(null, dto);
    }
}
