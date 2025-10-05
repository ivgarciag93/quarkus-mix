package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.ivione93.services.StravaApiService;

@ApplicationScoped
@Path("/v1/strava")
public class StravaApi {

    @Inject
    StravaApiService stravaService;

    @GET
    @Path("/auth")
    public Response getAuthToken(@QueryParam("code") final String code) {
        Log.info("Call to getAuthToken");
        return Response.ok().entity(stravaService.getAuthToken(code)).build();
    }

    @GET
    @Path("/refresh")
    public Response getRefreshToken() {
        Log.info("Call to getRefreshToken");
        return Response.ok().entity(stravaService.getRefreshToken()).build();
    }

    @GET
    @Path("/athlete")
    public Response getAthlete() {
        Log.info("Call to getAthlete");
        return Response.ok().entity(stravaService.getAthlete()).build();
    }

    @GET
    @Path("/athlete/{id}/stats")
    public Response getAthleteStats(@PathParam("id") final Integer id) {
        Log.infof("Call to getAthleteStats for id %d", id);
        return Response.ok().entity(stravaService.getAthleteStats(id)).build();
    }

}
