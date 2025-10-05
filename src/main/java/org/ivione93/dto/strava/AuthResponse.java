package org.ivione93.dto.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

    @JsonProperty("token_type")
    public String tokenType;

    @JsonProperty("expires_at")
    public Long expiresAt;

    @JsonProperty("expires_in")
    public Long expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("access_token")
    public String accessToken;

}
