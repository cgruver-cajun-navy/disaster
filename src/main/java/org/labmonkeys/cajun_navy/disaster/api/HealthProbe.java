package org.labmonkeys.cajun_navy.disaster.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class HealthProbe {

    @Produces
    @Readiness
    HealthCheck ready() {
        // TODO: Replace with a real probe
        return () -> HealthCheckResponse.named("ready").state(true).build();
    }

    @Produces
    @Liveness
    HealthCheck alive() {
        return () -> HealthCheckResponse.named("alive").up().build();
    }
}
