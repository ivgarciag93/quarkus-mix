package org.ivione93.dto.strava;

import jakarta.ws.rs.QueryParam;

public class AuthParams extends BaseAuthParams {

    @QueryParam("code")
    public String code;

    @QueryParam("grant_type")
    public String grantType;

    public AuthParams(String clientId, String clientSecret, String code, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.grantType = grantType;
    }

}
