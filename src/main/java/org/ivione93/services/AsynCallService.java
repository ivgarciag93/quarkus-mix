package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.ivione93.dto.ConfigDto;
import org.ivione93.dto.ItemInfo;
import org.ivione93.dto.ItemPrice;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.dto.accountdata.AccountStock;
import org.ivione93.dto.strava.*;
import org.ivione93.services.providers.CombinedService;
import org.ivione93.services.providers.FranchisesAccountService;
import org.ivione93.services.providers.PriceService;
import org.ivione93.services.providers.StravaService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class AsynCallService {

    @ConfigProperty(name = "bff.timeout.milliseconds")
    int timeoutMilliseconds;

    @Inject
    CombinedService combinedService;

    @Inject
    PriceService priceService;

    @Inject
    FranchisesAccountService franchisesAccountService;

    @Inject
    StravaService stravaService;

    @Inject
    ManagedExecutor managedExecutor;

    private CompletionException toCompletionException(final Throwable ex) {
        return ex instanceof CompletionException
                ? (CompletionException) ex
                : new CompletionException(ex);
    }

    /// /////////
    // STRAVA
    /// ////////
    public CompletableFuture<AuthResponse> getAuthToken(AuthParams authParams) {
        return managedExecutor
            .supplyAsync(() -> stravaService.getAuthToken(authParams))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining token in Strava");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<RefreshTokenResponse> getRefreshToken(RefreshTokenParams refreshTokenParams) {
        return managedExecutor
            .supplyAsync(() -> stravaService.getRefreshToken(refreshTokenParams))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error refreshing token in Strava");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AthleteResponse> getAthlete(String token) {
        return managedExecutor
            .supplyAsync(() -> stravaService.getAthlete(token))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining athlete info in Strava");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AthleteStatsResponse> getAthleteStats(String token, int athleteId) {
        return managedExecutor
            .supplyAsync(() -> stravaService.getAthleteStats(token, athleteId))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining athlete stats in Strava");
                throw toCompletionException(ex);
            });
    }

    /// /////////
    // COMBINED ITEMS
    /// ////////
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

    public CompletableFuture<List<ItemPrice>> getItemsPrice() {
        return managedExecutor
            .supplyAsync(() -> priceService.getItemsPrice())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining items price in pubsub-events-price");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AccountBalanceResponse> getOutstandingBalance(final int storeCode, final String fiscalId) {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getOutstandingBalance(storeCode, fiscalId))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining outstanding balance in account-data");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AccountBalanceResponse> getDailyMovements(final int storeCode, final String fiscalId) {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getDailyMovements(storeCode, fiscalId))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining daily movements in account-data");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AccountStock> getStock() {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getStock())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining stock in account-data");
                throw toCompletionException(ex);
            });
    }

}
