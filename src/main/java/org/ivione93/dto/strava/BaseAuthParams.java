package org.ivione93.dto.strava;

import jakarta.ws.rs.QueryParam;

public abstract class BaseAuthParams {

    @QueryParam("client_id")
    public String clientId;

    @QueryParam("client_secret")
    public String clientSecret;

}
