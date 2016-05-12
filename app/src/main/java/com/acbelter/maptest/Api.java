package com.acbelter.maptest;

import okhttp3.HttpUrl;

public class Api {
    public static HttpUrl buildFindRouteUrl(String startAddress, String finishAddress, String apiKey) {
        return HttpUrl.parse("https://maps.googleapis.com/maps/api/directions/json").newBuilder()
                .addQueryParameter("origin", startAddress)
                .addQueryParameter("destination", finishAddress)
                .addQueryParameter("key", apiKey)
                .addQueryParameter("departure_time", "now")
                .build();
    }
}