package org.tut.micro.fight.health;

import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.tut.micro.fight.service.FightService;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import org.tut.micro.fight.data.dao.Fight;

@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    @Inject
    FightService fightService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse
                .named("Fight Datasource connection health check");

        try {
            List<Fight> fights = fightService.findAllFights();
            responseBuilder.withData("Number of fights in the database", fights.size()).up();
        } catch (IllegalStateException e) {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}