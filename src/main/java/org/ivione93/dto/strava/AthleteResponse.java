package org.ivione93.dto.strava;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AthleteResponse {

    public String id;

    @JsonProperty("firstname")
    public String firstName;

    @JsonProperty("lastname")
    public String lastName;

    public String bic;

    @JsonProperty("created_at")
    public Instant createdAt;

    public String profile;
}
