package com.cookandroid.location_show;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WfMapApi {
    @GET("forecast") //baseline + direct?
    Call<WeatherForecastResponse> getWf( // + lat=?&lon=?&appid={?}&units=? 로 변환
                                         @Query("lat") double latitude,
                                         @Query("lon") double longitude,
                                         @Query("appid") String apiKey,
                                         @Query("units") String units,
                                         @Query("lang") String lang
    );
}
