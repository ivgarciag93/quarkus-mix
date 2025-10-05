package org.ivione93.dto.strava;

import jakarta.ws.rs.QueryParam;

public class RefreshTokenParams extends BaseAuthParams {

    @QueryParam("refresh_token")
    public String refreshToken;

    @QueryParam("grant_type")
    public String grantType;

    public RefreshTokenParams(String clientId, String clientSecret, String refreshToken, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
    }

}
