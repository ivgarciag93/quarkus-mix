package org.ivione93.services.dataservices;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ivione93.dto.strava.*;

@Path("")
@RegisterRestClient(configKey = "strava")
@RegisterForReflection
public interface StravaDataService {

    @POST
    @Path("/oauth/token")
    AuthResponse getAuthToken(@BeanParam AuthParams authParams);

    @POST
    @Path("/oauth/token")
    RefreshTokenResponse getRefreshToken(@BeanParam RefreshTokenParams refreshTokenParams);

    @GET
    @Path("/api/v3/athlete")
    AthleteResponse getAthlete(@HeaderParam("Authorization") String token);

    @GET
    @Path("/api/v3/athletes/{athleteId}/stats")
    AthleteStatsResponse getAthleteStats(@HeaderParam("Authorization") String token,
                                         @PathParam("athleteId") Integer athleteId);

}
