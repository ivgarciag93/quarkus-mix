package org.ivione93.services.async;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.ItemPrice;
import org.ivione93.services.providers.PriceService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PriceAsyncCallService extends AsyncCallService {

    @Inject
    PriceService priceService;

    public CompletableFuture<List<ItemPrice>> getItemsPrice() {
        return managedExecutor
            .supplyAsync(() -> priceService.getItemsPrice())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining items price in pubsub-events-price");
                throw toCompletionException(ex);
            });
    }

}
