package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ivione93.services.CsvService;
import org.jboss.resteasy.reactive.RestForm;

import java.io.InputStream;

@ApplicationScoped
@Path("/v1/csv")
public class CsvApi {

  @Inject
  CsvService csvService;

  @POST
  @Path("/upload-csv")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response processCsv(@RestForm("file") InputStream file) {
    Log.infof("Call to processCsv");
    if (file == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    return Response.ok().entity(csvService.processCsv(file)).build();
  }

}
