/*
AirPollutionResponse.java
이 파일은 대기오염 api 데이터를 호출받아서 각각의 선언된 변수에 어노테이션된 함수를 통해 값을 할당하는 클래스이다.
response.get()으로 받은 객체를 클래스명과 get()함수로 파싱하여 원하는 데이터를 변수에 할당하는 방식

그 외 ~response.java 파일도 형태가 동일하다.
 */

package com.syu.WeatherApp.requestApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirPollutionResponse {
    @SerializedName("list")
    private List<Air> airList;
    public List<Air> getAirList(){ return airList; }

    public static class Air{
        @SerializedName("main")
        private airMain main;
        public airMain getMain() {return main;}

        @SerializedName("components")
        private components comp;
        public components getComp() {return comp;}
    }
    public static class airMain{
        @SerializedName("aqi")
        private int aqi;
        public int getAqi() { return aqi; }
    }
    public static class components{
        @SerializedName("co")
        private double co;
        public double getCo(){ return co; }
        @SerializedName("o3")
        private double o3;
        public double getO3(){ return o3; }
        @SerializedName("pm2_5")
        private double pm2_5;
        public double getPm2_5(){ return pm2_5; }
        @SerializedName("pm10")
        private double pm10;
        public double getPm10(){ return pm10; }

    }
}