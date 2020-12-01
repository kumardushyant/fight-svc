package org.tut.micro.fight;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;
import org.tut.micro.fight.data.Fighter;
import org.tut.micro.fight.data.dao.Fight;
import org.tut.micro.fight.service.FightService;

@Path("/fight")
@Produces(MediaType.APPLICATION_JSON)
public class FightResource {

	private static final Logger LOGGER = Logger.getLogger(FightResource.class);

    @Inject
    FightService service;

    @GET
    @Path("/randomfighters")
    public Response getRandomFighters() throws InterruptedException {
        try{
            Fighter fighters = service.findRandomFighters();
            LOGGER.debug("Get random fighters " + fighters);
            return Response.ok(fighters).build();
        } catch (Throwable e) {
            LOGGER.error(e);
            throw e;
        }
    }

    @GET
    public Response getAllFights() {
        List<Fight> fights = service.findAllFights();
        LOGGER.debug("Total number of fights " + fights);
        return Response.ok(fights).build();
    }

   @GET
    @Path("/{id}")
    public Response getFight(@PathParam("id") Long id) {
        Fight fight = service.findFightById(id);
        if (fight != null) {
            LOGGER.debug("Found fight " + fight);
            return Response.ok(fight).build();
        } else {
            LOGGER.debug("No fight found with id " + id);
            return Response.noContent().build();
        }
    }

    @POST
    public Fight fight(@Valid Fighter fighters, @Context UriInfo uriInfo) {
        return service.persistFight(fighters);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "hello";
    }
}