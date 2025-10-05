package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.ConfigDto;
import org.ivione93.dto.ItemInfo;
import org.ivione93.services.dataservices.CombinedDataService;

import java.util.List;

@ApplicationScoped
public class CombinedService {

    @RestClient
    CombinedDataService combinedDataService;

    public ConfigDto getConfig() {
        return combinedDataService.getConfig();
    }

    public List<ItemInfo> getItemsInfo() {
        return combinedDataService.getItemsInfo();
    }

}
