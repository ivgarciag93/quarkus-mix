package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.ivione93.dto.strava.*;
import org.ivione93.services.dataservices.StravaDataService;

@ApplicationScoped
public class StravaService {

    @RestClient
    StravaDataService stravaDataService;

    public AuthResponse getAuthToken(AuthParams authParams) {
        return stravaDataService.getAuthToken(authParams);
    }

    public RefreshTokenResponse getRefreshToken(RefreshTokenParams refreshTokenParams) {
        return stravaDataService.getRefreshToken(refreshTokenParams);
    }

    public AthleteResponse getAthlete(String token) {
        return stravaDataService.getAthlete(token);
    }

    public AthleteStatsResponse getAthleteStats(String token, int athleteId) {
        return stravaDataService.getAthleteStats(token, athleteId);
    }

}
