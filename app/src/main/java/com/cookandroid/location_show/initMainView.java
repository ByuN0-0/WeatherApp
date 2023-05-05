package com.cookandroid.location_show;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class initMainView {
    private static initMainView Instance;

    private initMainView(){}

    LinearLayout[] Lay_Inside;
    TextView[] TV;
    private TextView temperatureTextView;
    private TextView locationDescription;
    private TextView locationText;
    private static TextView forecastText;
    private TextView airqualityText;
    private TextView COText;
    private TextView O3Text;
    private TextView pm10Text;
    private TextView pm2_5Text;

    private static LinearLayout scRollTimeWeather;
    private static LinearLayout scRollDayWeather;

    public static initMainView getInstance(){
        if(Instance == null){
            synchronized (initMainView.class){
                Instance = new initMainView();
            }
        }
        return Instance;
    }
    public void setTemperatureTextView(String temperatureString){
        this.temperatureTextView.setText(temperatureString + "℃");      //온도
    }
    public void setLocationDescription(String locationDescription){
        this.locationDescription.setText("현재 날씨는 " + locationDescription);      //날씨
    }
    public void setLocationText(String koloc){
        this.locationText.setText(koloc + " 날씨");       //지역
    }
    public static void setForecastText(String s, int index){
        if(index == 0){
            forecastText.setText(s);
        } else if(index == 1){
            forecastText.setText(s);
        } else if(index == 2){
            forecastText.setText(s);
        } else if(index == 3){
            forecastText.setText(s);
        }
    }
    public void setAirqualityText(String aqi){
        airqualityText.setText("미세먼지 농도: "+aqi);
    }
    public void setCOText(String CO){
        COText.setText(CO);
    }
    public void setO3Text(String O3){
        O3Text.setText(O3);
    }
    public void setPm10Text(String pm10){
        pm10Text.setText(pm10);
    }
    public void setPm2_5Text(String pm2_5){
        pm2_5Text.setText(pm2_5);
    }
    public static LinearLayout getscRollTimeWeather(){
        return scRollTimeWeather;
    }
    public static LinearLayout getscRollDayWeather(){
        return scRollDayWeather;
    }

    private void initWidget(MainActivity mainActivity){
        temperatureTextView = mainActivity.findViewById(R.id.temperature_text_view); //온도 텍스트뷰
        locationDescription = mainActivity.findViewById(R.id.location_description); //날씨 텍스트뷰

        locationText = mainActivity.findViewById(R.id.locationText);
        forecastText = mainActivity.findViewById(R.id.forecastText);
        airqualityText = mainActivity.findViewById(R.id.airquality_text);
        COText = mainActivity.findViewById(R.id.COtext);
        O3Text = mainActivity.findViewById(R.id.O3text);
        pm10Text = mainActivity.findViewById(R.id.pm10text);
        pm2_5Text = mainActivity.findViewById(R.id.pm2_5text);

        scRollDayWeather = mainActivity.findViewById(R.id.scRollDayweather);
        scRollTimeWeather = mainActivity.findViewById(R.id.scRollTimeWeather);

    }

    public void initView(MainActivity mainActivity){
        TabHost tabHost = mainActivity.getTabHost();
        Lay_Inside = new LinearLayout[4];
        TV = new TextView[4];

        for(int i = 0; i < Lay_Inside.length; i++){
            Lay_Inside[i] = new LinearLayout(mainActivity.getApplicationContext());
            TV[i] = new TextView(mainActivity.getApplicationContext());
            if(i == 0){
                TV[i].setText("현재날씨");
                TV[i].setTextColor(Color.parseColor("#ffffff"));
                TV[i].setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                TV[i].setLayoutParams(LPS);
                LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                LPS1.setMargins(0,0,3,0);
                LPS1.weight = 1;
                Lay_Inside[i].setLayoutParams(LPS1);
                Lay_Inside[i].setGravity(Gravity.CENTER);
                Lay_Inside[i].addView(TV[i]);
                Lay_Inside[i].setBackgroundResource(R.drawable.round);
                TabHost.TabSpec weatherNow = tabHost.newTabSpec("weatherNow").setIndicator(Lay_Inside[i]);
                weatherNow.setContent(R.id.weatherNow);
                tabHost.addTab(weatherNow);
            } else if(i == 1){
                TV[i].setText("일기예보");
                TV[i].setTextColor(Color.parseColor("#ffffff"));
                TV[i].setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                TV[i].setLayoutParams(LPS);
                LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                LPS1.setMargins(0,0,3,0);
                LPS1.weight = 1;
                Lay_Inside[i].setLayoutParams(LPS1);
                Lay_Inside[i].setGravity(Gravity.CENTER);
                Lay_Inside[i].addView(TV[i]);
                Lay_Inside[i].setBackgroundResource(R.drawable.round);
                TabHost.TabSpec foreCast = tabHost.newTabSpec("foreCast").setIndicator(Lay_Inside[i]);
                foreCast.setContent(R.id.foreCast);
                tabHost.addTab(foreCast);
            } else if(i == 2){
                TV[i].setText("미세먼지");
                TV[i].setTextColor(Color.parseColor("#ffffff"));
                TV[i].setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                TV[i].setLayoutParams(LPS);
                LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                LPS1.setMargins(0,0,3,0);
                LPS1.weight = 1;
                Lay_Inside[i].setLayoutParams(LPS1);
                Lay_Inside[i].setGravity(Gravity.CENTER);
                Lay_Inside[i].addView(TV[i]);
                Lay_Inside[i].setBackgroundResource(R.drawable.round);
                TabHost.TabSpec airPollution = tabHost.newTabSpec("airPollution").setIndicator(Lay_Inside[i]);
                airPollution.setContent(R.id.airPollution);
                tabHost.addTab(airPollution);
            } else if(i == 3){
                TV[i].setText("기타");
                TV[i].setTextColor(Color.parseColor("#ffffff"));
                TV[i].setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                TV[i].setLayoutParams(LPS);
                LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                LPS1.setMargins(0,0,0,0);
                LPS1.weight = 1;
                Lay_Inside[i].setLayoutParams(LPS1);
                Lay_Inside[i].setGravity(Gravity.CENTER);
                Lay_Inside[i].addView(TV[i]);
                Lay_Inside[i].setBackgroundResource(R.drawable.round);
                TabHost.TabSpec Etc = tabHost.newTabSpec("Etc").setIndicator(Lay_Inside[i]);
                Etc.setContent(R.id.Etc);
                tabHost.addTab(Etc);
            }
        }
        tabHost.setBackgroundColor(Color.parseColor("#1d1d1d"));
        tabHost.setCurrentTab(0);
        initWidget(mainActivity);
    }

}

//////////////////////////////////////////////////////////////////////////백업///////////////////////////////////////////////////////////////////////////
//        tabHost.setBackground(mainActivity.getResources().getDrawable(R.drawable.round));
//        TabHost.TabSpec weatherNow = tabHost.newTabSpec("weatherNow").setIndicator("현재날씨",mainActivity.getResources().getDrawable(R.drawable.round_left));
//        weatherNow.setContent(R.id.weatherNow);
//        tabHost.addTab(weatherNow);
//
//        TabHost.TabSpec foreCast = tabHost.newTabSpec("foreCast").setIndicator("일기예보",mainActivity.getResources().getDrawable(R.drawable.square));
//        foreCast.setContent(R.id.foreCast);
//        tabHost.addTab(foreCast);
//
//        TabHost.TabSpec airPollution = tabHost.newTabSpec("airPollution").setIndicator("미세먼지",mainActivity.getResources().getDrawable(R.drawable.square));
//        airPollution.setContent(R.id.airPollution);
//        tabHost.addTab(airPollution);
//
//        TabHost.TabSpec Etc = tabHost.newTabSpec("Etc").setIndicator("기타",mainActivity.getResources().getDrawable(R.drawable.round_right));
//        Etc.setContent(R.id.Etc);
//        tabHost.addTab(Etc);