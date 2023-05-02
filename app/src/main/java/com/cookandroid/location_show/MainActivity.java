package com.cookandroid.location_show;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends TabActivity {

    //double latitude = 37.6168328;
    //double longitude = 127.1055345;

    private GeoMapApi Geoapi; //Geocoding API
    String apiKey = "163034e395f4430a30d20336ccc67b22";
    String units = "metric";
    String apilang = "kr";
    private TextView temperatureTextView;
    private TextView locationLatitude;
    private TextView locationLongitude;
    private TextView locationDescription;

    SimpleDateFormat dformat = new SimpleDateFormat("aa hh:mm:ss");
    TextView DemonTime;
    String allDay;
    private TextView testview;

    LinearLayout scRollTimeWeather, scRollDayWeather;
    scrollViewinit svWidget;
    private TextView test1;
    private TextView test2;

    @SuppressLint({"MissingInflatedId","deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadWeatherData(latitude, longitude);
        //loadWeatherForecastData(latitude, longitude);
        //loadAirPollutionData(latitude, longitude);
        System.out.print("정상 실행됨!!");

        temperatureTextView = findViewById(R.id.temperature_text_view); //온도 텍스트뷰
        locationLatitude = findViewById(R.id.location_latitude); //위도
        locationLongitude = findViewById(R.id.location_longitude); //경도
        locationDescription = findViewById(R.id.location_description); //날씨 텍스트뷰
        test1 = findViewById(R.id.test1);
        test2 = findViewById(R.id.test2);
        testview = findViewById(R.id.test);

        DemonTime = (TextView) findViewById(R.id.t1);

        ShowTimeMethod();
        initMainView init = initMainView.getInstance();
        init.initView(this);

        scRollTimeWeather = (LinearLayout) findViewById(R.id.scRollTimeWeather);
        scRollDayWeather = (LinearLayout) findViewById(R.id.scRollDayweather);
        try {
            svWidget = scrollViewinit.getInstance();
            svWidget.addView(MainActivity.this, scRollTimeWeather, 0);
            svWidget.addView(MainActivity.this, scRollDayWeather, 1);
        }catch(NullPointerException e){
            Log.e("null","sv = null");
        }
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        }
        else{
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            String provider = location.getProvider();
//            double altitude = location.getAltitude();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            loadWeatherData(latitude,longitude);
            loadWeatherForecastData(latitude, longitude);
            loadAirPollutionData(latitude, longitude);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }

    }
    private void loadWeatherData(double latitude, double longitude) {
        Retrofit retroCurrentWeatherApi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // retrofit 라이브러리 초기화
        CWmapApi CWapi = retroCurrentWeatherApi.create(CWmapApi.class);

        Call<CurrentWeatherResponse> CWcall = CWapi.getCurrentWeather(latitude, longitude, apiKey, units, apilang);
        // 초기화한 라이브러리로 call 객체에 api JSON파일을 가져옴
        CWcall.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    double temperature = response.body().getWeatherData().getTemperature();
                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
                    String temperatureString = String.format("%.1f", temperature);
                    locationDescription.setText("현재 날씨는 " + descriptionString);
                    locationLatitude.setText("위도" + latitude);
                    locationLongitude.setText("경도" + longitude);
                    temperatureTextView.setText(temperatureString + "℃");
                    svWidget.TimeWeatherReload(MainActivity.this, scRollTimeWeather, temperatureString);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                // API 호출 실패
                temperatureTextView.setText("api 호출 실패!!!");
            }
        });
    }
    private void loadWeatherForecastData(double latitude, double longitude){
        Retrofit retroWFapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WFmapApi WFapi = retroWFapi.create(WFmapApi.class);
        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units);
        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    double temp = response.body().getForecastList().get(0).getMain().getTemperature();
                    String dttxt = response.body().getForecastList().get(0).getDt_txt();
                    String iconUrl = response.body().getForecastList().get(0).getWeatherList().get(0).getIcon();
                    loadImage(test2, iconUrl);
                    //test1.setText(String.format("%.1f'C", temp));

                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                // API 호출 실패
                test1.setText("api 호출 실패!!!"+ t.getMessage());
            }
        });
    }
    private void loadAirPollutionData(double latitude, double longitude){
        Retrofit retroAPapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APmapApi APapi = retroAPapi.create(APmapApi.class);
        Call<AirPollutionResponse> APcall = APapi.getAP(latitude, longitude, apiKey);
        APcall.enqueue(new Callback<AirPollutionResponse>() {
            @Override
            public void onResponse(Call<AirPollutionResponse> call, Response<AirPollutionResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    int aqi = response.body().getAirList().get(0).getMain().getAqi();
                    test1.setText(Integer.toString(aqi));

                }
            }
            @Override
            public void onFailure(Call<AirPollutionResponse> call, Throwable t) {
                // API 호출 실패
                test1.setText("api 호출 실패!!!"+ t.getMessage());
            }
        });
    }
    public String convertToLocal(String dt_txt) {
        String localTime = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateUtcTime = dateFormat.parse(dt_txt);
            int offset = 9; // offset 값은 로컬 시간대에 따라 다를 수 있습니다.
            TimeZone timeZone = TimeZone.getTimeZone("GMT+" + offset);
            Calendar calendar = Calendar.getInstance(timeZone);
            calendar.setTimeInMillis(dateUtcTime.getTime());
            Date dateLocalTime = calendar.getTime();
            localTime = dateFormat.format(dateLocalTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localTime;
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            String provider = location.getProvider();
//            double altitude = location.getAltitude();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            loadWeatherData(latitude,longitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void ShowTimeMethod(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date d = new Date();
                allDay = DateFormat.getDateTimeInstance().format(d);
                DemonTime.setText(dformat.format(d));
            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
    public void loadImage(TextView tv, String iconUrl){
        String url = "https://openweathermap.org/img/w/"+iconUrl+".png";
        tv.setText("");
        Glide.with(MainActivity.this).load(url).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                tv.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        tv.setWidth(150);
        tv.setHeight(150);
    }
}