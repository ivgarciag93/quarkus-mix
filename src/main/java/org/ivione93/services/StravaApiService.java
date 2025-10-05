package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.strava.*;
import org.ivione93.services.dataservices.StravaDataService;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class StravaApiService {

    @ConfigProperty(name = "strava.client-id")
    String clientId;

    @ConfigProperty(name = "strava.client-secret")
    String clientSecret;

    public static final String GRANT_TYPE_AUTH = "authorization_code";
    public static final String GRANT_TYPE_REFRESH = "refresh_token";

    public static String token;
    public static String refreshToken;

    @RestClient @Inject
    StravaDataService stravaDataService;

    @Inject
    AsynCallService asynCallService;

    public AuthResponse getAuthToken(final String code) {
        AuthParams authParams = new AuthParams(clientId, clientSecret, code, GRANT_TYPE_AUTH);

        final CompletableFuture<AuthResponse> futureAuthResponse =
            asynCallService.getAuthToken(authParams);

        try {
            AuthResponse authResponse = futureAuthResponse.get();
            if (authResponse != null) {
                token = "Bearer " + authResponse.accessToken;
                refreshToken = authResponse.refreshToken;
            }
            return authResponse;
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining Strava auth token");
            throw new WebApplicationException("Unable to obtain Strava auth token", ex);
        }
    }

    public RefreshTokenResponse getRefreshToken() {
        RefreshTokenParams refreshTokenParams =
            new RefreshTokenParams(clientId, clientSecret, refreshToken, GRANT_TYPE_REFRESH);

        final CompletableFuture<RefreshTokenResponse> futureRefreshTokenResponse =
            asynCallService.getRefreshToken(refreshTokenParams);

        try {
            RefreshTokenResponse refreshTokenResponse = futureRefreshTokenResponse.get();
            if (refreshTokenResponse != null) {
                token = "Bearer " + refreshTokenResponse.accessToken;
                refreshToken = refreshTokenResponse.refreshToken;
            }
            return refreshTokenResponse;
        } catch (Exception ex) {
            Log.errorf(ex, "Error while refreshing Strava auth token");
            throw new WebApplicationException("Unable to refresh Strava auth token", ex);
        }
    }

    public AthleteResponse getAthlete() {
        try {
            return asynCallService.getAthlete(token).get();
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining Strava athlete info");
            throw new WebApplicationException("Unable to obtain Strava athlete info", ex);
        }
    }

    public AthleteStatsResponse getAthleteStats(Integer athleteId) {
        try {
            return asynCallService.getAthleteStats(token, athleteId).get();
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining Strava athlete stats");
            throw new WebApplicationException("Unable to obtain Strava athlete stats", ex);
        }
    }

}
