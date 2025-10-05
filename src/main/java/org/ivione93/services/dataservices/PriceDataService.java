package org.ivione93.services.dataservices;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ivione93.dto.ItemPrice;

import java.util.List;

@Path("/v1/pubsub-events-price/prices")
@RegisterRestClient(configKey = "pubsub-events-price")
@RegisterForReflection
public interface PriceDataService {

    @GET
    @Path("/all")
    List<ItemPrice> getItemsPrice();

}
