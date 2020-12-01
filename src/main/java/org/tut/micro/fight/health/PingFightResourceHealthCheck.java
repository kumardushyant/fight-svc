package org.tut.micro.fight.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.tut.micro.fight.FightResource;
import org.tut.micro.fight.service.FightService;

@Liveness
@ApplicationScoped
public class PingFightResourceHealthCheck implements HealthCheck {

    @Inject
    FightResource fightService;
    
    public HealthCheckResponse call() {
        fightService.hello();
        return HealthCheckResponse.named("Ping Fight REST Endpoint").up().build();
    }
}
