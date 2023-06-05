/*
WeatherForecastResponse.java
이 파일은 기상 예측 api 데이터를 호출받아서 각각의 선언된 변수에 어노테이션된 함수를 통해 값을 할당하는 클래스이다.
response.get()으로 받은 객체를 클래스명과 get()함수로 파싱하여 원하는 데이터를 변수에 할당하는 방식

그 외 ~response.java 파일도 형태가 동일하다.
 */

package com.syu.WeatherApp.requestApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastResponse {

    @SerializedName("list")
    private List<forecastList> forecastlist;
    public List<WeatherForecastResponse.forecastList> getForecastList() { return forecastlist; }

    public static class forecastList { //여기서 json객체의 값을 @serializedName("json변수명")으로 리턴 받으면됨
        @SerializedName("dt")
        private int dt;
        public int getDT() { return dt;}

        @SerializedName("main")
        private forecastMain main;
        public forecastMain getMain() { return main; }
        @SerializedName("weather")
        private List<WeatherForecastResponse.weatherList> weatherList;
        public List<WeatherForecastResponse.weatherList> getWeatherList() { return weatherList; }
        @SerializedName("dt_txt")
        private String dt_txt;
        public String getDt_txt() { return dt_txt; }
    }

    public static class forecastMain{
        @SerializedName("temp")
        private double temp;

        public double getTemperature() {
            return temp;
        }

    }
    public static class weatherList {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }

        @SerializedName("icon")
        private String icon;
        public String getIcon() { return icon; }
    }
}