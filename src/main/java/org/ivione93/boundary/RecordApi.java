package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.ivione93.services.RecordService;

@ApplicationScoped
@Path("/v1/records")
public class RecordApi {

  @Inject
  RecordService recordService;

  @GET
  public Response getRecords() {
    Log.info("Call to getRecords");
    return Response.ok().entity(recordService.getRecords()).build();
  }
}
