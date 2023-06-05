package com.syu.WeatherApp;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;
import com.syu.WeatherApp.view.ProgressDialog;
import com.syu.WeatherApp.view.ScrollViewinit;
import com.syu.WeatherApp.view.initMainView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    private TextView locationLatitude;
    private TextView locationLongitude;
    public static boolean LocationLoadComplete, WeatherDataLoadComplete, WeatherForecastDataLoadComplete, AirPollutionDataLoadComplete, GeoDataLoadComplete;
    private static TransitionDrawable transition;
    private FrameLayout MainFrame;
    private int TimeCount = 0;
    private int apiCount = 0;
    private int apiTimeCount = 0;
    public WeatherView weatherView;
    private ImageView ImView;
    static Animation animationMoveAlpha;
    private static final Integer[] alpha_draw = { R.drawable.alpha_draw0, R.drawable.alpha_draw1, R.drawable.alpha_draw2, R.drawable.alpha_draw3,
            R.drawable.alpha_draw4, R.drawable.alpha_draw5, R.drawable.alpha_draw6, R.drawable.alpha_draw7, R.drawable.alpha_draw8};
    private static int[] bgTest;
    private static int bgTmp;
    private static int bgTmp1;
    private int single = 0;
    private int single1 = 0;
    private int single2 = 0;
    private int single3 = 0;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat Ct = new SimpleDateFormat("HH");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat Ct1 = new SimpleDateFormat("mm");
    String CtSum;
    Integer CurrentTime;
    Integer CurrentMinute;
    private double longitude;
    private double latitude;
    int apiCount1 = 0;
    public boolean StarState = true;            //Todo

    @SuppressLint({"MissingInflatedId", "deprecation", "WrongViewCast", "SimpleDateFormat"})
    SimpleDateFormat dformat = new SimpleDateFormat("aa hh:mm:ss");
    TextView DemonTime;
    public static String allDay;
    public static int TimeCnt;

    public ProgressDialog PD;
    public Timer TM;

    private ScrollViewinit svWidget;
    private initMainView init;
    private LoadAllData allData;

    @SuppressLint({"MissingInflatedId", "deprecation", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setContentView(R.layout.activity_main);

        String apiKey = null;
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            apiKey = appInfo.metaData.getString("com.example.apikey");
            // API 키를 사용하여 외부 API 호출
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        LoadingMotion();

        locationLatitude = findViewById(R.id.location_latitude); //위도
        locationLongitude = findViewById(R.id.location_longitude); //경도

        DemonTime = (TextView) findViewById(R.id.t1);

        MainFrame = (FrameLayout) findViewById(android.R.id.tabcontent);
        MainFrame.setBackgroundResource(R.drawable.alpha_draw0);
        transition = (TransitionDrawable) MainFrame.getBackground();

        weatherView = findViewById(R.id.weather_view);
//        weatherView.setEmissionRate(60f);       //떨어지는 개수의 정도
        weatherView.setFadeOutPercent(10000f);         //입자 선명도
        weatherView.setWeatherData(PrecipType.CLEAR);        //날씨 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            weatherView.setForceDarkAllowed(true);
        }

        ImView = (ImageView) findViewById(R.id.ImView);
        ImView.setTranslationX(1200);       //오른쪽 상단의 좌표. 만약 500이면 가운데임
        animationMoveAlpha = AnimationUtils.loadAnimation(this, R.anim.falling_star);
//        ImView.startAnimation(animationMoveAlpha);      //Todo

        Date dt = new Date();
        String CtSt = Ct.format(dt);
        CurrentTime = Integer.parseInt(CtSt);
        String Ctst1 = Ct1.format(dt);
        CurrentMinute = Integer.parseInt(Ctst1);
        CtSum = CtSt + Ctst1;
        System.out.println("-------------------------현재 시간 : "+CurrentTime);            //Todo
        System.out.println("-------------------------현재 분 : "+CurrentMinute);           //Todo


        ShowTimeMethod();
        init = initMainView.getInstance();
        init.initView(this);

        allData = LoadAllData.getInstance();
        allData.setApiKey(apiKey);

        svWidget = ScrollViewinit.getInstance();

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT < 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        } else{
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            locationLatitude.setText("위도" + latitude);
            locationLongitude.setText("경도" + longitude);
            System.out.println(latitude);          //Todo
            System.out.println(longitude);          //Todo
            LocationLoadComplete = true;

            if(apiCount<5) {
                apiCount++;
                allData.loadWeatherData(latitude, longitude, svWidget, init);
                allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this);
                allData.loadAirPollutionData(latitude, longitude, init);
                allData.loadGeoData(latitude, longitude, init);
                System.out.println("처음 위치에 대한 api호출");          //Todo
            }

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            String provider = location.getProvider();
//            double altitude = location.getAltitude();
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            if(apiCount<5) {
                apiCount++;
                allData.loadWeatherData(latitude, longitude, svWidget, init);
                allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this);
                allData.loadAirPollutionData(latitude, longitude, init);
                allData.loadGeoData(latitude, longitude, init);
                System.out.println("바뀐 위치에 대한 api호출");          //Todo
            }

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void ShowTimeMethod(){
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date d = new Date();
                allDay = DateFormat.getDateTimeInstance().format(d);        //year-month-day aMpM xx:xx:xx
                DemonTime.setText(dformat.format(d));

                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                System.out.println("--------------------------"+hour+":"+minute+":"+second+"--------------------------");       //Todo

                if(hour == 0 && minute == 0 && second == 0){
                    bgTest = LoadAllData.test;
                }

                if (minute == 0 && apiCount1 == 0) {
                    apiCount1++;
                    loadApiData(latitude,longitude);
                } else if(minute != 0 && apiCount1 != 0){
                    apiCount1 = 0;
                }

                TimeCount++;
                System.out.println(TimeCount);          //Todo
//                System.out.println("------------------------------------별 상태 : "+StarState);      //Todo

                if(second % 10 == 5 && (bgTest[11] == 8 || bgTest[11] == 0) && TimeCount >= 10 && StarState){
                    ImView.startAnimation(animationMoveAlpha);
                }

                apiTimeCount++;
                if(apiTimeCount == 60){
                    apiCount = 0;
                    apiTimeCount = 0;
                }

                if(single3 == 0){
                    single3 = 1;
                    if(bgTest == null){
                        single3 = 0;
                        TimeCount--;
                    } else {
                        System.out.println("---------------------------------------처음 출력");          //Todo
                        System.out.println("---------------------------------------일출 : "+Opacity.sunrise);          //Todo
                        System.out.println("---------------------------------------일몰 : "+Opacity.sunset);          //Todo
                        System.out.println("-------------------------------------------인덱스 : " + bgTest[11]);          //Todo
                        System.out.println("----------------------------------------------분 : " + bgTest[10]);          //Todo
                        System.out.println("---------------------------------------밀리초 계산 : " + bgTest[10] * 1000 * 60);          //Todo
                        System.out.println("------------------------------------------초 계산 : " + bgTest[10] * 60);          //Todo
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        transition = (TransitionDrawable) MainFrame.getBackground();
                        bgTmp = bgTest[10] * 60;
//                        transition.startTransition(bgTmp);    //초 변환
                        transition.startTransition(bgTest[10] * 60 * 1000);
                        System.out.println("--------------------------------------현재 지속시간 : "+bgTest[10] * 60);          //Todo
                        TimeCount = 0;
//                        bgTmp = (bgTmp / 1000) + 1;           //초 변환
                        System.out.println("--------------------------------------------bgTmp : "+bgTmp);          //Todo
                        System.out.println("--------------------------------------------일몰 시간 : "+Integer.parseInt(Opacity.sunsetInt));          //Todo
                        System.out.println("--------------------------------------------일몰 분 : "+Integer.parseInt(Opacity.sunsetInt1));          //Todo
                        System.out.println("--------------------------------------------일몰 시간 + 분 : "+Integer.parseInt(Opacity.sunsetSum));          //Todo
                        System.out.println("--------------------------------------------현재 시간 + 분 : "+Integer.parseInt(CtSum));          //Todo
                    }
                } else if((bgTest != null) && (TimeCount == bgTmp)){
                    if((CurrentTime <= 23 && Integer.parseInt(CtSum) >= Integer.parseInt(Opacity.sunsetSum)) && single1 == 0){
                        single1++;
                        bgTmp1 = 9;
//                        System.out.println("-----------------------------------bgTmp가 현재 9");          //Todo
                    }
                    if(bgTmp1 == 9){
                        System.out.println("-----------------------------------bgTmp가 현재 9");          //Todo
                        bgTmp = -1;
                        bgTmp1 = 0;
                        bgTest[11] = 8;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                    } else if(bgTest[11] == 8){
                        bgTest[11] = 0;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 0){
                        bgTest[11] = 1;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 1){
                        bgTest[11] = 2;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 2){
                        bgTest[11] = 3;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 3){
                        bgTest[11] = 4;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 4) {
                        bgTest[11] = 5;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : " + bgTest[11]);          //Todo
                    } else if(bgTest[11] == 5){
                        bgTest[11] = 6;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 6){
                        bgTest[11] = 7;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    } else if(bgTest[11] == 7){
                        bgTest[11] = 8;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);          //Todo
                    }

                    if(bgTmp == -1){
                        bgTmp = bgTmp + 1;
                    } else{
                        bgTmp = bgTest[11] + 1;
                    }
                    if(bgTmp == 9){
                        bgTmp1 = bgTmp;
                    }

                    System.out.println("------------------------------------------현재 bgTmp : "+bgTmp);          //Todo
                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(bgTest[bgTmp] * 60);         //초 변환
                    transition.startTransition(bgTest[bgTmp] * 60 * 1000);
                    System.out.println("------------------------------------------바뀐 지속시간 : "+bgTest[bgTmp] * 60);          //Todo
                    bgTmp = bgTest[bgTmp] * 60;
//                    bgTmp = ((bgTest[bgTmp] * 60) / 1000)+1;                //초 변환
                    TimeCount = 0;
                }
                if(TimeCnt >= 10 && TimeCnt % 10 == 0){
                    System.out.println("토스트 실행");          //Todo
                    Toast.makeText(getApplicationContext(),"인터넷 연결 또는 GPS연결을 확인해주세요.",Toast.LENGTH_LONG).show();          //Todo
                }
            }
        };
        Runnable task = () -> {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    //noinspection BusyWait
                    Thread.sleep(1000);
                }catch (InterruptedException e){Log.e("error","interruptedexception");}
                handler.sendEmptyMessage(1);
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void LoadingMotion(){
        if(single == 1)     //안하면 api failure에서 이 메소드 계속 호출
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
                System.out.println("대기시간 : "+TimeCnt+"초");          //Todo
                if(WeatherDataLoadComplete && WeatherForecastDataLoadComplete && LocationLoadComplete && AirPollutionDataLoadComplete
                        && GeoDataLoadComplete && LoadAllData.test != null){
                    if(single2 == 0){
                        bgTest = LoadAllData.test;
                        TimeCount = 0;
                        single2 = 1;
//                        System.out.println("--------------------------------TimeCount 초기화");          //Todo
                    }
                    TimeCnt = 0;
                    TM.cancel();
                    PD.dismiss();
                    single = 0;
                    System.out.println("TimeCnt초기화");          //Todo
                }
            }
        };
        TM.schedule(TM_T, 1000, 1000);
    }

    private void loadApiData(double latitude, double longitude){
        allData.loadWeatherData(latitude, longitude, svWidget, init);
        allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this);
        allData.loadAirPollutionData(latitude, longitude, init);
        allData.loadGeoData(latitude, longitude, init);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(PD != null && PD.isShowing())
            PD.dismiss();
        moveTaskToBack(true);
        finishAndRemoveTask();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}