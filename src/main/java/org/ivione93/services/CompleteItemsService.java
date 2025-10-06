package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.ivione93.dto.CompletedItemsResponse;
import org.ivione93.dto.ConfigDto;
import org.ivione93.dto.ItemInfo;
import org.ivione93.dto.ItemPrice;
import org.ivione93.services.async.CombinedAsyncCallService;
import org.ivione93.services.async.PriceAsyncCallService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@ApplicationScoped
public class CompleteItemsService {

    @Inject
    CombinedAsyncCallService combinedAsyncCallService;

    @Inject
    PriceAsyncCallService priceAsyncCallService;

    public CompletedItemsResponse getCombined() {

        final CompletableFuture<ConfigDto> futureConfig =
            combinedAsyncCallService.getConfig();

        final CompletableFuture<List<ItemInfo>> futureInfo =
            combinedAsyncCallService.getItemsInfo();

        final CompletableFuture<List<ItemPrice>> futurePrice =
            priceAsyncCallService.getItemsPrice();

        try {
            CompletableFuture.allOf(futureConfig, futureInfo, futurePrice).join();
            return this.fillCompleteItems(
                futureConfig.get(),
                futureInfo.get(),
                futurePrice.get());
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining combined items");
            throw new WebApplicationException("Unable to obtain combined items", ex);
        }
    }

    private CompletedItemsResponse fillCompleteItems(
            ConfigDto config, List<ItemInfo> itemsInfo, List<ItemPrice> itemsPrice
    ) {
        CompletedItemsResponse response = new CompletedItemsResponse();
        response.items = new ArrayList<>();
        response.items = combine(itemsInfo, itemsPrice);
        response.items.forEach(itemResponse -> {
            itemResponse.minStayDate = itemResponse.creationDate.plus(config.minStay, ChronoUnit.DAYS);
            itemResponse.isRemovable = Instant.now().isAfter(itemResponse.minStayDate);
        });

        response.currentSize = itemsInfo.size();
        response.maxSize = config.maxSize;
        response.minStay = config.minStay;

        return response;
    }

    private List<CompletedItemsResponse.ItemResponse> combine(List<ItemInfo> items, List<ItemPrice> prices) {
        Map<Integer, Double> priceMap = prices.stream()
            .collect(Collectors.toMap(
                p -> p.itemCode,
                p -> p.retailPrice));

        return items.stream()
            .map(item -> new CompletedItemsResponse.ItemResponse(
                item.itemCode,
                item.itemDescription,
                priceMap.getOrDefault(item.itemCode, 0.0),
                item.creationDate))
            .collect(Collectors.toList());
    }
}
