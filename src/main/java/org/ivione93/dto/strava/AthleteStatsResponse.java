package org.ivione93.dto.strava;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AthleteStatsResponse {

    @JsonProperty("biggest_ride_distance")
    public double biggestRideDistance;
    @JsonProperty("biggest_climb_elevation_gain")
    public double biggestClimbElevationGain;

    @JsonProperty("recent_ride_totals")
    public Totals recentRideTotals;
    @JsonProperty("all_ride_totals")
    public Totals allRideTotals;
    @JsonProperty("recent_run_totals")
    public Totals recentRunTotals;
    @JsonProperty("all_run_totals")
    public Totals allRunTotals;
    @JsonProperty("recent_swim_totals")
    public Totals recentSwimTotals;
    @JsonProperty("all_swim_totals")
    public Totals allSwimTotals;
    @JsonProperty("ytd_ride_totals")
    public Totals ytdRideTotals;
    @JsonProperty("ytd_run_totals")
    public Totals ytdRunTotals;
    @JsonProperty("ytd_swim_totals")
    public Totals ytdSwimTotals;

    public static class Totals {
        public int count;
        public double distance;
        @JsonProperty("moving_time")
        public double movingTime;
        @JsonProperty("elapsed_time")
        public double elapsedTime;
        @JsonProperty("elevation_gain")
        public double elevationGain;
        @JsonProperty("achievement_count")
        public int achievementCount;
    }
}
