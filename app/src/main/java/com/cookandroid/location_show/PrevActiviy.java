package com.cookandroid.location_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;


public class PrevActiviy extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT < 23) {
            Log.d("VERSION_TAG", "Version is under 23");
            onClickShowAlert(0);
        } else {
            // requestPermission의 배열의 index가 아래 grantResults index와 매칭
            // 퍼미션이 승인되면
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION_TAG", "Permission: " + permissions[0] + " was " + grantResults[0]);
                Log.d("PERMISSION_TAG", "Permission: " + permissions[1] + " was " + grantResults[1]);
                Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                // TODO : 퍼미션이 승인되는 경우에 대한 코드

            }
            // 퍼미션이 승인 거부되면
            else {
                Log.d("PERMISSION_TAG", "Permission denied");
                onClickShowAlert(1);
                // TODO : 퍼미션이 거부되는 경우에 대한 코드
            }
        }
    }

    public void onClickShowAlert(int index) {
        AlertDialog.Builder AlertBuilder = new AlertDialog.Builder(PrevActiviy.this);
        if (index == 0) {
            AlertBuilder.setTitle("Version");
            AlertBuilder.setMessage("지원하지 않는 버전입니다.");
            AlertBuilder.setPositiveButton("종료", (dialogInterface, i) -> finish());
        } else if (index == 1) {
            AlertBuilder.setTitle("권한");
            AlertBuilder.setMessage("권한 설정을 해주셔야 합니다.");
            AlertBuilder.setPositiveButton("종료", (dialogInterface, i) -> finish());
        }
        AlertBuilder.show();
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_activiy);

        if (!hasPermissions(PrevActiviy.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(PrevActiviy.this, PERMISSIONS, PERMISSION_ALL);
        }
        // 권한이 허용되어있다면 다음 화면 진행
        else {
            Intent intent = new Intent(PrevActiviy.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}





////////////////////////////////////////////////////////////////백업본////////////////////////////////////////////////////////////////////////////
//    final LocationListener gpsLocationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
////            String provider = location.getProvider();
////            double altitude = location.getAltitude();
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
////            loadWeatherData(latitude,longitude);
//        }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        public void onProviderEnabled(String provider) {
//        }
//
//        public void onProviderDisabled(String provider) {
//        }
//    };

//    private void LocationRunner(LocationManager lm) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        double longitude = location.getLongitude();
//        double latitude = location.getLatitude();
//
//        loadWeatherData(latitude, longitude);
//        loadWeatherForecastData(latitude, longitude);
//
////        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
////        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
//    }
//
//    private void loadWeatherForecastData(double latitude, double longitude){
//        Retrofit retroWFapi = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        WfMapApi WFapi = retroWFapi.create(WfMapApi.class);
//        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units, apilang);
//        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
//            @Override
//            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
//                // api 호출 성공
//                if (response.isSuccessful()) {
//                    double[] tempList = new double[40];
//                    String[] icoList = new String[40];
//                    String[] weatherList = new String[40];
//                    for(int i=0;i<40;i++){
//                        tempList[i]= response.body().getForecastList().get(i).getMain().getTemperature();
//                        icoList[i]= response.body().getForecastList().get(i).getWeatherList().get(0).getIcon();
//                        weatherList[i] = response.body().getForecastList().get(i).getWeatherList().get(0).getDescription();
//                    }
//
//                    svWidget.setTempList(tempList);
//                    svWidget.setIcoList(icoList);
//                    svWidget.setWeatherList(weatherList);
//                    svWidget.initView();
//                }
//            }
//            @Override
//            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
//                // API 호출 실패
//
//            }
//        });
//    }
//    public void loadWeatherData(double latitude, double longitude) {
//        Retrofit retroCurrentWeatherApi = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // retrofit 라이브러리 초기화
//        CWmapApi CWapi = retroCurrentWeatherApi.create(CWmapApi.class);
//
//        Call<CurrentWeatherResponse> CWcall = CWapi.getCurrentWeather(latitude, longitude, apiKey, units, apilang);
//        // 초기화한 라이브러리로 call 객체에 api JSON파일을 가져옴
//        CWcall.enqueue(new Callback<CurrentWeatherResponse>() {
//            @Override
//            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
//                // api 호출 성공
//                if (response.isSuccessful()) {
//                    double temperature = response.body().getWeatherData().getTemperature();
//                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
//                    String icon = response.body().getWeatherList().get(0).getIcon();
//                    String temperatureString = String.format("%.1f", temperature);
//
//                    svWidget.setPresentWeather(descriptionString);
//                    svWidget.setCurrentIco(icon);
//                    svWidget.setPresentTemp(temperatureString);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
//                // API 호출 실패
//            }
//        });
//    }