package com.cookandroid.location_show;

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