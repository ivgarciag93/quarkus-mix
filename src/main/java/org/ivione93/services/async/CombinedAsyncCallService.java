package org.ivione93.services.async;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.ConfigDto;
import org.ivione93.dto.ItemInfo;
import org.ivione93.services.providers.CombinedService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class CombinedAsyncCallService extends AsyncCallService {

    @Inject
    CombinedService combinedService;

    public CompletableFuture<ConfigDto> getConfig() {
        return managedExecutor
            .supplyAsync(() -> combinedService.getConfig())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining config in pubsub-events");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<List<ItemInfo>> getItemsInfo() {
        return managedExecutor
            .supplyAsync(() -> combinedService.getItemsInfo())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining items info in pubsub-events");
                throw toCompletionException(ex);
            });
    }

}
