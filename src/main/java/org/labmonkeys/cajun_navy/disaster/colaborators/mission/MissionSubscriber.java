package org.labmonkeys.cajun_navy.disaster.colaborators.mission;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.vertx.mutiny.core.eventbus.EventBus;

@ApplicationScoped
public class MissionSubscriber {

    @Inject
    EventBus bus;

    @Incoming("mission-rejected")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<CompletionStage<Void>> missionRejected(Message<MissionDTO> mission) {
        return CompletableFuture.supplyAsync(() -> {
            bus.request("mission-rejected", mission.getPayload()).onItem().transform(msg -> msg.body());
            return mission.ack();
        });
    }
}
