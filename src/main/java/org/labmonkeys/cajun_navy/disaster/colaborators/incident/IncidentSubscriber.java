package org.labmonkeys.cajun_navy.disaster.colaborators.incident;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.vertx.mutiny.core.eventbus.EventBus;

@ApplicationScoped
public class IncidentSubscriber {
    @Inject
    EventBus bus;

    @Incoming("incident-reported")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<CompletionStage<Void>> incidentReported (Message<IncidentDTO> message) {
        return CompletableFuture.supplyAsync(() -> {
            bus.request("assign-incident", message.getPayload()).onItem().transform(msg -> msg.body());
            return message.ack();
        });
    }
}
