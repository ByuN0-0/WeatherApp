/*
CWmapApi.java
CurrentWeather Mapping- API
retrofit2의 Call과 http.GET, http.Query 클래스를 이용해서 원하는 api를 호출하기 위해 링크 생성 및 연결
@GET("") <- baseurl 설정
@Query("") <- api 매개변수 설정
 */
package com.syu.WeatherApp.requestApi;

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
