package org.labmonkeys.cajun_navy.disaster.colaborators.mission;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class MissionPublisher {
    
    private final UnicastProcessor<MissionDTO> missionProcessor = UnicastProcessor.create();

    public void createMission(MissionDTO dto) {
        missionProcessor.onNext(dto);
    }

    @Outgoing("create-mission")
    public Multi<Message<MissionDTO>> missionMulti() {
        return missionProcessor.onItem().transform(this::sendMission);
    }

    private Message<MissionDTO> sendMission(MissionDTO dto) {
        return KafkaRecord.of(null, dto);
    }
}
