package com.cookandroid.location_show;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoMapApi {
    @GET("direct") //baseline + direct?
    Call<GeoResponse> getGeo( // + lat=?&lon=?&appid={?}&units=? 로 변환
                              @Query("q") String city_name,
                              @Query("limit") double state_code,
                              @Query("appid") String apiKey

    );

    @GET("reverse")
    Call<List<GeoResponse>> getReGeo(
                            @Query("lat") double lat,
                            @Query("lon") double lon,
                            @Query("appid") String apiKey
    );
}
