package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.ItemPrice;
import org.ivione93.services.dataservices.PriceDataService;

import java.util.List;

@ApplicationScoped
public class PriceService {

    @RestClient
    PriceDataService priceDataService;

    public List<ItemPrice> getItemsPrice() {
        return priceDataService.getItemsPrice();
    }

}
