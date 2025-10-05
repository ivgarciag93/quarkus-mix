package org.ivione93;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ivione93.dto.strava.AuthResponse;
import org.ivione93.services.StravaApiService;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
public class StravaApiTest {

    private static final String BASE_STRAVA_URI = "/v1/strava";
    private static final String GET_AUTH_TOKEN_URI = BASE_STRAVA_URI + "/auth";

    private static final String STRAVA_CODE = "strava-code";

    @InjectMock
    StravaApiService stravaService;

    @Test
    void testGetAuthToken() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.accessToken = "valid_access_token";
        authResponse.refreshToken = "valid_refresh_token";

        when(stravaService.getAuthToken(anyString())).thenReturn(authResponse);

        AuthResponse result =
            given()
                .queryParam("code", STRAVA_CODE)
                .when()
                .get(GET_AUTH_TOKEN_URI)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AuthResponse.class);

        assertThat(result).usingRecursiveComparison().isEqualTo(authResponse);
    }
}
