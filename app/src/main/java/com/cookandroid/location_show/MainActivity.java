package com.cookandroid.location_show;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TabActivity;

import android.content.Context;
import android.content.pm.PackageManager;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends TabActivity {
    String apiKey = "163034e395f4430a30d20336ccc67b22";
    String units = "metric";
    String apilang = "kr";
    private TextView temperatureTextView;
    private TextView locationLatitude;
    private TextView locationLongitude;
    private TextView locationDescription;
    private TextView locationText;
    private TextView forecastText;
    private TextView airqualityText;
    private TextView COText;
    private TextView O3Text;
    private TextView pm10Text;
    private TextView pm2_5Text;
    SimpleDateFormat dformat = new SimpleDateFormat("aa hh:mm:ss");
    TextView DemonTime;
    String allDay;
    String strlat;
    String strlon;
    LinearLayout scRollTimeWeather, scRollDayWeather;
    scrollViewinit svWidget;

    @SuppressLint({"MissingInflatedId", "deprecation", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        double latitude;
        double longitude;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.print("정상 실행됨!!");

        temperatureTextView = findViewById(R.id.temperature_text_view); //온도 텍스트뷰
        locationLatitude = findViewById(R.id.location_latitude); //위도
        locationLongitude = findViewById(R.id.location_longitude); //경도
        locationDescription = findViewById(R.id.location_description); //날씨 텍스트뷰
        locationText = findViewById(R.id.locationText);
        forecastText = findViewById(R.id.forecastText);
        DemonTime = findViewById(R.id.t1);
        airqualityText = findViewById(R.id.airquality_text);
        COText = findViewById(R.id.COtext);
        O3Text = findViewById(R.id.O3text);
        pm10Text = findViewById(R.id.pm10text);
        pm2_5Text = findViewById(R.id.pm2_5text);

        ShowTimeMethod();
        initMainView init = initMainView.getInstance();
        init.initView(this);

        scRollTimeWeather = findViewById(R.id.scRollTimeWeather);
        scRollDayWeather = findViewById(R.id.scRollDayweather);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) {
                latitude = 37.6168328;
                longitude = 127.1055345;
            } else {
//              String provider = location.getProvider(); 현재 사용안함
//              double altitude = location.getAltitude(); 현재 사용안함
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            // 스크롤뷰 위젯 생성
            svWidget = scrollViewinit.getInstance();
            svWidget.setLoc(latitude, longitude);
            svWidget.addView(MainActivity.this, scRollTimeWeather, 0);
            svWidget.addView(MainActivity.this, scRollDayWeather, 1);

            String strlat = String.format("%.4f", latitude);
            String strlon = String.format("%.4f", longitude);

            locationLatitude.setText("위도" + strlat);
            locationLongitude.setText("경도" + strlon);

            loadWeatherData(latitude, longitude);
            loadWeatherForecastData(latitude, longitude);
            loadAirPollutionData(latitude, longitude); //Todo
            loadGeoData(latitude, longitude);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }
    }

    public void loadWeatherData(double latitude, double longitude) {
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
                    String icon = response.body().getWeatherList().get(0).getIcon();
                    String temperatureString = String.format("%.1f", temperature);
                    locationDescription.setText("현재 날씨는 " + descriptionString);
                    temperatureTextView.setText("현재 기온: "+ temperatureString + "℃");
                    svWidget.setPresentWeather(descriptionString);
                    svWidget.setCurrentIco(icon);
                    svWidget.setPresentTemp(temperatureString);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                // API 호출 실패
                temperatureTextView.setText("api 호출 실패!!!");
            }
        });
    }

    private void loadWeatherForecastData(double latitude, double longitude) {
        Retrofit retroWFapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WFmapApi WFapi = retroWFapi.create(WFmapApi.class);
        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units, apilang);
        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    double[] tempList = new double[40];
                    String[] icoList = new String[40];
                    String[] weatherList = new String[40];
                    for (int i = 0; i < 40; i++) {
                        tempList[i] = response.body().getForecastList().get(i).getMain().getTemperature();
                        icoList[i] = response.body().getForecastList().get(i).getWeatherList().get(0).getIcon();
                        weatherList[i] = response.body().getForecastList().get(i).getWeatherList().get(0).getDescription();
                    }
                    for (int i = 0; i < 40; i++) {
                        icoList[i] = response.body().getForecastList().get(i).getWeatherList().get(0).getIcon();
                    }
                    checkRain(icoList);

                    svWidget.setTempList(tempList);
                    svWidget.setIcoList(icoList);
                    svWidget.setWeatherList(weatherList);
                    svWidget.TimeWeatherReload(MainActivity.this, scRollTimeWeather);
                    svWidget.DayWeatherReload(MainActivity.this, scRollDayWeather);
                    //test1.setText(String.format("%.1f'C", temp));

                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                // API 호출 실패

            }
        });
    }

    private void loadAirPollutionData(double latitude, double longitude) {
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
                    double CO = response.body().getAirList().get(0).getComp().getCo();
                    double O3 = response.body().getAirList().get(0).getComp().getO3();
                    double pm10 = response.body().getAirList().get(0).getComp().getPm10();
                    double pm2_5 = response.body().getAirList().get(0).getComp().getPm2_5();
                    airqualityText.setText("미세먼지 농도: "+straqi(aqi));
                    COText.setText(String.format("%.2fμg/m3",CO));
                    O3Text.setText(String.format("%.2fμg/m3",O3));
                    pm10Text.setText(String.format("%.2fμg/m3",pm10));
                    pm2_5Text.setText(String.format("%.2fμg/m3",pm2_5));

                }
            }

            @Override
            public void onFailure(Call<AirPollutionResponse> call, Throwable t) {
                // API 호출 실패

            }
        });
    }

    private void loadGeoData(double latitude, double longitude) {
        Retrofit retroGeoapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/geo/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GeomapApi Geoapi = retroGeoapi.create(GeomapApi.class);

        Call<List<GeoResponse>> Geocall = Geoapi.getReGeo(latitude, longitude, apiKey);
        Geocall.enqueue(new Callback<List<GeoResponse>>() {
            @Override
            public void onResponse(Call<List<GeoResponse>> call, Response<List<GeoResponse>> response) {
                if (response.isSuccessful()) {
                    String koloc = response.body().get(0).getLocalName().getLocal_ko();
                    locationText.setText(koloc + " 날씨");
                }
            }

            @Override
            public void onFailure(Call<List<GeoResponse>> call, Throwable t) {

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
            //String provider = location.getProvider();
            //double altitude = location.getAltitude();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            strlat = String.format("%.4f", latitude);
            strlon = String.format("%.4f", longitude);
            locationLatitude.setText("위도" + strlat);
            locationLongitude.setText("경도" + strlon);
            loadWeatherData(latitude, longitude);
            loadWeatherForecastData(latitude, longitude);
            loadAirPollutionData(latitude, longitude);
            loadGeoData(latitude, longitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
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
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e("error","interruptedexception");
                    }
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void checkRain(String[] weatherlist) { // 0: 눈,비 없음, 1: 비, 2: 눈,  3: 눈,비
        boolean rain = false;
        boolean snow = false;
        for (int i = 0; i < 8; i++) {
            if (weatherlist[i].equals("09d") || weatherlist[i].equals("10d") || weatherlist[i].equals("11d") || weatherlist[i].equals("09n") || weatherlist[i].equals("10n") || weatherlist[i].equals("11n")) {
                rain = true;
            }
            if (weatherlist[i].equals("13d") || weatherlist[i].equals("13n")) {
                snow = true;
            }
        }
        if (rain || snow) {
            if (rain && snow) {
                forecastText.setText("오늘 한때 눈이나 비가 예상됩니다.");
            } else if (rain) {
                forecastText.setText("오늘 한때 비가 예상됩니다.");
            } else {
                forecastText.setText("오늘 한때 눈이 예상됩니다.");
            }
        } else {
            forecastText.setText("오늘은 눈이나 비가 예상되지 않습니다.");
        }
    }

    public String straqi(int aqi) {
        if (aqi == 1) {
            return "아주좋음";
        }
        if (aqi == 2) {
            return "좋음";
        }
        if (aqi == 3) {
            return "보통";
        }
        if (aqi == 4) {
            return "나쁨";
        }
        else{
            return "아주나쁨";
        }
    }
}