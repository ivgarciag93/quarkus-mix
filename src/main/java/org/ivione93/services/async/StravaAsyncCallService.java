package org.ivione93.services.async;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.strava.*;
import org.ivione93.services.providers.StravaService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class StravaAsyncCallService extends AsyncCallService {

    @Inject
    StravaService stravaService;

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

}
