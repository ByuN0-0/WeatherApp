package com.syu.WeatherApp.requestApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APmapApi {
    @GET("air_pollution")
    Call<AirPollutionResponse> getAP(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey
    );
}