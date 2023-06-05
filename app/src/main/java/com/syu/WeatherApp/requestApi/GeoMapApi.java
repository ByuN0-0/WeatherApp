/*
GeoMapApi.java
Geometry Mapping- API
retrofit2의 Call과 http.GET, http.Query 클래스를 이용해서 원하는 api를 호출하기 위해 링크 생성 및 연결
@GET("") <- baseurl 설정
@Query("") <- api 매개변수 설정
 */

package com.syu.WeatherApp.requestApi;

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
