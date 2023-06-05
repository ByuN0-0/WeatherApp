package com.syu.WeatherApp.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.syu.WeatherApp.MainActivity;
import com.syu.WeatherApp.R;

@SuppressWarnings("deprecation")
public class InitMainView {
    @SuppressLint("StaticFieldLeak")
    private static InitMainView Instance;

    private InitMainView(){}

    LinearLayout[] Lay_Inside;
    TextView[] TV;
    private TextView temperatureTextView;
    private TextView locationDescription;
    private TextView locationText;
    @SuppressLint("StaticFieldLeak")
    private static TextView forecastText;
    private TextView airqualityText;
    private TextView COText;
    private TextView O3Text;
    private TextView pm10Text;
    private TextView pm2_5Text;
    private TextView sunriseText;
    private TextView sunsetText;
    private TextView windDegText;
    private TextView windSpeedText;
    private TextView rainAmountText;
    private TextView feelTempText;
    private TextView humidityText;
    private TextView visibilityText;
    private TextView cloudsText;
    private TextView pressureText;

    @SuppressLint("StaticFieldLeak")
    private static LinearLayout scRollTimeWeather;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout scRollDayWeather;

    public static InitMainView getInstance(){
        if(Instance == null){
            synchronized (InitMainView.class){
                Instance = new InitMainView();
            }
        }
        return Instance;
    }
    @SuppressLint("SetTextI18n")
    public void setTemperatureTextView(String temperatureString){
        this.temperatureTextView.setText(temperatureString + "℃");      //온도
    }
    @SuppressLint("SetTextI18n")
    public void setLocationDescription(String locationDescription){
        this.locationDescription.setText("현재 날씨는 " + locationDescription);      //날씨
    }
    @SuppressLint("SetTextI18n")
    public void setLocationText(String koloc){
        this.locationText.setText(koloc + " 날씨");       //지역
        MainActivity.GeoDataLoadComplete=true;      //Todo 이 부분 바뀜
    }
    @SuppressLint("SetTextI18n")
    public void setSunsetText(String sunsetText){
        this.sunsetText.setText("일몰 \n"+sunsetText);
    }
    @SuppressLint("SetTextI18n")
    public void setSunriseText(String sunriseText){
        this.sunriseText.setText("일출 \n"+sunriseText);
    }
    @SuppressLint("SetTextI18n")
    public void setWindSpeedText(String windSpeed){
        this.windSpeedText.setText("풍속 : "+ windSpeed + "m/s");
    }
    @SuppressLint("SetTextI18n")
    public void setWindDegText(String windDeg){
        this.windDegText.setText("풍향 : "+ windDeg);
    }
    @SuppressLint("SetTextI18n")
    public void setFeelTempText(String feelTemp){
        this.feelTempText.setText("체감온도 : "+feelTemp+"℃");
    }
    @SuppressLint("SetTextI18n")
    public void setHumidityText(String humidity){
        this.humidityText.setText("습도 : "+humidity+"%");
    }
    @SuppressLint("SetTextI18n")
    public void setVisibilityText(String visibility){
        this.visibilityText.setText("가시거리는\n"+visibility+"m 입니다.");
    }
    @SuppressLint("SetTextI18n")
    public void setCloudsText(String clouds){
        this.cloudsText.setText("흐림도는\n"+clouds+"% 입니다.");
    }
    @SuppressLint("SetTextI18n")
    public void setPressureText(String pressure){
        this.pressureText.setText("대기압 : "+pressure+" hPa");
    }
    @SuppressLint("SetTextI18n")
    public void setRainAmountText(String rainAmount){
        this.rainAmountText.setText("지난 1시간 강수량은 "+rainAmount+"mm입니다.");
    }
    public static void setForecastText(String s){
        forecastText.setText(s);
    }
    @SuppressLint("SetTextI18n")
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
        MainActivity.AirPollutionDataLoadComplete=true;     //Todo 이 부분 바뀜
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

        windSpeedText =  mainActivity.findViewById(R.id.windSpeed);
        sunsetText = mainActivity.findViewById(R.id.sunset);
        sunriseText = mainActivity.findViewById(R.id.sunrise);
        windDegText = mainActivity.findViewById(R.id.windDeg);
        feelTempText = mainActivity.findViewById(R.id.feelTemp);
        humidityText = mainActivity.findViewById(R.id.humidity);
        visibilityText = mainActivity.findViewById(R.id.visibility);
        cloudsText = mainActivity.findViewById(R.id.clouds);
        pressureText = mainActivity.findViewById(R.id.pressure);
        rainAmountText = mainActivity.findViewById(R.id.rainAmount);

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