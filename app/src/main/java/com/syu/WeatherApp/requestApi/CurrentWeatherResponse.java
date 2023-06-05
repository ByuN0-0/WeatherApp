/*
CurrentWeatherResponse.java
이 파일은 현재 기상정보 api 데이터를 호출받아서 각각의 선언된 변수에 어노테이션된 함수를 통해 값을 할당하는 클래스이다.
response.get()으로 받은 객체를 클래스명과 get()함수로 파싱하여 원하는 데이터를 변수에 할당하는 방식

그 외 ~response.java 파일도 형태가 동일하다.
 */

package com.syu.WeatherApp.requestApi;

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
    public List<Weather> getWeatherList() { return weatherList; }
    @SerializedName("visibility")
    private double visibility;
    public double getVisibility() { return visibility; }
    @SerializedName("wind")
    private WindData winddata;
    public WindData getWindData() { return winddata; }
    @SerializedName("clouds")
    private CloudData clouddata;
    public CloudData getCloudData() { return clouddata; }
    @SerializedName("sys")
    private SysData sysdata;
    public SysData getSysdata() { return sysdata; }

    @SerializedName("rain")
    private RainData raindata;
    public RainData getRaindata() {return raindata;}

    public static class WeatherData { //여기서 json객체의 값을 @serializedName("json변수명")으로 리턴 받으면됨
        @SerializedName("temp") //기온
        private double temperature;
        public double getTemperature() { return temperature; }

        @SerializedName("feels_like") //체감온도
        private double feeltemp;
        public double getFeeltemp() { return feeltemp; }

        @SerializedName("humidity") //습도
        private double humidity;
        public double getHumidity() { return humidity; }

        @SerializedName("pressure")
        private double pressure;
        public double getPressure() { return pressure; }
        @SerializedName("grnd_level") //대기압
        private double grnd_level;
        public double getGrnd_level() { return grnd_level; }

        @SerializedName("temp_min") //최저온도
        private double temp_min;
        public double getTemp_min() { return temp_min; }

        @SerializedName("temp_max")
        private double temp_max;
        public double getTemp_max() { return temp_max; }
    }

    public static class Weather {
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;
        public String getDescription() {
            return description;
        }
        public String getIcon() { return icon; }
    }

    public static class WindData {
        @SerializedName("speed") //풍속
        private double windspeed;
        public double getWindspeed() { return windspeed; }
        @SerializedName("deg") //풍향
        private double winddeg;
        public double getWinddeg() { return winddeg; }
    }
    public static class CloudData{
        @SerializedName("all")
        private double cloud;
        public double getCloud() { return cloud; }
    }
    public static class RainData{
        @SerializedName("3h")
        private Double rainamount;
        public Double getRainAmount() { return rainamount; }
    }
    public static class SysData{
        @SerializedName("sunrise")
        private String sunrise;
        public String getSunrise() { return sunrise; }
        @SerializedName("sunset")
        private String sunset;
        public String getSunset() { return sunset;}
    }
}