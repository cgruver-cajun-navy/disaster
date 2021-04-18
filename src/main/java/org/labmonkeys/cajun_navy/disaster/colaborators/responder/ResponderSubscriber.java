package org.labmonkeys.cajun_navy.disaster.colaborators.responder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.vertx.mutiny.core.eventbus.EventBus;

@ApplicationScoped
public class ResponderSubscriber {

    @Inject
    EventBus bus;

    @Incoming("responder-available")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<CompletionStage<Void>> responderAvailable(Message<ResponderDTO> responder) {
        return CompletableFuture.supplyAsync(() -> {
            bus.request("responder-available", responder.getPayload()).onItem().transform(msg -> msg.body());
            return responder.ack();
        });
    }
}
