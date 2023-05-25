package com.cookandroid.location_show;
//push를 위한 주석

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TabActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends TabActivity {
    private static int TimeCnt;
    private TextView locationLatitude;
    private TextView locationLongitude;
    public static String sunrise = "07시 00분";
    public static String sunset = "20시 30분";
    public static boolean LocationLoadComplete, WeatherDataLoadComplete, WeatherForecastDataLoadComplete, AirPollutionDataLoadComplete, GeoDataLoadComplete;
    @SuppressLint({"MissingInflatedId", "deprecation", "WrongViewCast"})
    SimpleDateFormat dformat = new SimpleDateFormat("aa hh:mm:ss");
    TextView DemonTime;
    String allDay, strLat, strLon;
    public ProgressDialog PD;
    public Timer TM;

    private int single = 0;
    scrollViewinit svWidget;
    initMainView init;
    LoadAllData allData;

    @SuppressLint({"MissingInflatedId","deprecation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        double latitude;
        double longitude;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingMotion();

        locationLatitude = findViewById(R.id.location_latitude); //위도
        locationLongitude = findViewById(R.id.location_longitude); //경도

        DemonTime = findViewById(R.id.t1);

        ShowTimeMethod();
        init = initMainView.getInstance();
        init.initView(this);

        allData = LoadAllData.getInstance();

        svWidget = scrollViewinit.getInstance();

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

            strLat = String.format("%.6f", latitude);
            strLon = String.format("%.6f", longitude);

            locationLatitude.setText("위도" + strLat);
            locationLongitude.setText("경도" + strLon);

            allData.loadWeatherData(latitude, longitude, svWidget, init);
            allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this, init);
            allData.loadAirPollutionData(latitude, longitude, init);
            allData.loadGeoData(latitude, longitude, init);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //String provider = location.getProvider();
            //double altitude = location.getAltitude();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            if (location == null) {
                latitude = 37.6168328;
                longitude = 127.1055345;
            } else {
//              String provider = location.getProvider(); 현재 사용안함
//              double altitude = location.getAltitude(); 현재 사용안함
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            strLat = String.format("%.6f", latitude);
            strLon = String.format("%.6f", longitude);
            locationLatitude.setText("위도" + strLat);
            locationLongitude.setText("경도" + strLon);

            LocationLoadComplete = true;

            allData.loadWeatherData(latitude, longitude, svWidget, init);
            allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this, init);
            allData.loadAirPollutionData(latitude, longitude, init);
            allData.loadGeoData(latitude, longitude, init);
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
                if(TimeCnt >= 15 && TimeCnt % 10 == 0){
                    System.out.println("토스트 실행");
                    Toast.makeText(getApplicationContext(),"인터넷 연결 또는 GPS연결을 확인해주세요.",Toast.LENGTH_LONG).show();
                }
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
    public void LoadingMotion(){
        if(single == 1)     //메소드 싱글톤 안하면 api failure에서 이 메소드 계속 호출
            return;
        single++;

        PD = new ProgressDialog(this);
        PD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PD.setCancelable(false);

        TM = new Timer();
        PD.show();
        TimeCnt = 0;

        TimerTask TM_T = new TimerTask() {
            @Override
            public void run() {
                TimeCnt++;
                System.out.println(TimeCnt);
                if(WeatherDataLoadComplete && WeatherForecastDataLoadComplete && LocationLoadComplete && AirPollutionDataLoadComplete && GeoDataLoadComplete){
                    TimeCnt = 0;
                    TM.cancel();
                    PD.dismiss();
                    single = 0;
                    System.out.println("TimeCnt초기화");
                }
            }
        };
        TM.schedule(TM_T, 1000, 1000);
    }
}