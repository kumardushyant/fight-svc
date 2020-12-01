package org.tut.micro.fight.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.tut.micro.fight.data.Hero;

@Path("/hero")
@RegisterRestClient
public interface HeroService {

	@GET
	@Path("random")
	@Produces(MediaType.APPLICATION_JSON)
	Hero getRandomHero();
}
