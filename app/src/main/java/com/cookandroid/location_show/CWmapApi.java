package com.cookandroid.location_show;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CWmapApi {
    @GET("weather") //baseline + weather?
    Call<CurrentWeatherResponse> getCurrentWeather( // + lat=?&lon=?&appid={?}&units=? 로 변환
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("appid") String apiKey,
        @Query("units") String units,
        @Query("lang") String lang

    );
}
