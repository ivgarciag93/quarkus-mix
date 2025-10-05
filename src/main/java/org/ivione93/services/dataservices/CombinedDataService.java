package org.ivione93.services.dataservices;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.ivione93.dto.ConfigDto;
import org.ivione93.dto.ItemInfo;

import java.util.List;

@Path("/v1/pubsub-events/items")
@RegisterRestClient(configKey = "pubsub-events")
@RegisterForReflection
public interface CombinedDataService {

    @GET
    @Path("/config")
    ConfigDto getConfig();

    @GET
    @Path("/info")
    List<ItemInfo> getItemsInfo();

}
