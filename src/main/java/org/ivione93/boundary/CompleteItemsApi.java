package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ivione93.services.CompleteItemsService;

@ApplicationScoped
@Path("v1/pubsub-events-bff")
public class CompleteItemsApi {

    @Inject
    CompleteItemsService completeItemsService;

    @GET
    @Path("/items/combined")
    public Response getCombined() {
        Log.info("Call to getCombined");
        return Response.ok().entity(completeItemsService.getCombined()).build();
    }
}
