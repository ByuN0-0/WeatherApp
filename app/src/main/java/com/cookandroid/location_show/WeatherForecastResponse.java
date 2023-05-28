package com.cookandroid.location_show;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//public class WeatherForecastResponse {
//
//    @SerializedName("list")
//    private List<forecastList> forecastlist;
//
//    public List<WeatherForecastResponse.forecastList> getForecastList() { return forecastlist; }
//
//
//
//    @SerializedName("main")
//    private CurrentWeatherResponse.WeatherData weatherData;
//    @SerializedName("weather")
//    private List<CurrentWeatherResponse.Weather> weatherList;
//
//    public CurrentWeatherResponse.WeatherData getWeatherData() {
//        return weatherData;
//    }
//
//    public List<CurrentWeatherResponse.Weather> getWeatherList() {
//        return weatherList;
//    }
//
//    public static class forecastList { //여기서 json객체의 값을 @serializedName("json변수명")으로 리턴 받으면됨
//        @SerializedName("dt")
//        private int dt;
//        public double getDT() { return dt;}
//
//        @SerializedName("main")
//        private forecastMain main;
//        public forecastMain getMain() { return main;}
//    }
//
//    public static class forecastMain{
//        @SerializedName("temp")
//        private double temp;
//
//        public double getTemperature() {
//            return temp;
//        }
//
//    }
//
//    public static class Weather {
//        @SerializedName("description")
//        private String description;
//
//        public String getDescription() {
//            return description;
//        }
//    }
//}


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