package com.cookandroid.location_show;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {
    @SerializedName("main")
    private WeatherData weatherData;
    public WeatherData getWeatherData() {
        return weatherData;
    }
    @SerializedName("weather")
    private List<Weather> weatherList;
    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public static class WeatherData { //여기서 json객체의 값을 @serializedName("json변수명")으로 리턴 받으면됨
        @SerializedName("temp")
        private double temperature;
        @SerializedName("humidity")
        private double humidity;

        public double getTemperature() {
            return temperature;
        }

        public double getHumidity() {
            return humidity;
        }
    }

    public static class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
