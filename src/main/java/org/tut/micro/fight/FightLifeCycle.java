package org.tut.micro.fight;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@ApplicationScoped
public class FightLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(FightLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("FIGHT API");
        LOGGER.info("The application FIGHT is starting with profile "+ ProfileManager.getActiveProfile());
    }

    void onStop(@Observes ShutdownEvent evt) {
        LOGGER.info("Shutting down FLIGHT API");
    }
}