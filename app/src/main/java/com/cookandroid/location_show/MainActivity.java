package com.cookandroid.location_show;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
import android.widget.Toast;

import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    private TextView locationLatitude;
    private TextView locationLongitude;
    public static boolean LocationLoadComplete, WeatherDataLoadComplete, WeatherForecastDataLoadComplete, AirPollutionDataLoadComplete, GeoDataLoadComplete;
    private static TransitionDrawable transition;      //Todo
    private FrameLayout MainFrame;      //Todo
    private int TimeCount = 0;      //Todo
    private ImageView ImView;       //Todo
    static Animation animationMoveAlpha;           //Todo
    private static final Integer[] alpha_draw = { R.drawable.alpha_draw0, R.drawable.alpha_draw1, R.drawable.alpha_draw2, R.drawable.alpha_draw3,
            R.drawable.alpha_draw4, R.drawable.alpha_draw5, R.drawable.alpha_draw6, R.drawable.alpha_draw7, R.drawable.alpha_draw8};        //Todo
    private static int[] bgTest;          //Todo
    private static int bgTmp;       //Todo
    private static int bgTmp1;
    private int single1 = 0;        //Todo
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat Ct = new SimpleDateFormat("HH");          //Todo
    SimpleDateFormat Ct1 = new SimpleDateFormat("mm");          //Todo
    String CtSum;       //Todo
    Integer CurrentTime;        //Todo
    Integer CurrentMinute;       //Todo

    @SuppressLint({"MissingInflatedId", "deprecation", "WrongViewCast", "SimpleDateFormat"})
    SimpleDateFormat dformat = new SimpleDateFormat("aa hh:mm:ss");
    TextView DemonTime;
    public static String allDay;

    public static int TimeCnt;

    public ProgressDialog PD;
    public Timer TM;

    private int single = 0;             //Todo
    private scrollViewinit svWidget;
    private initMainView init;
    private LoadAllData allData;

    @SuppressLint({"MissingInflatedId", "deprecation", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingMotion();

        locationLatitude = findViewById(R.id.location_latitude); //위도
        locationLongitude = findViewById(R.id.location_longitude); //경도

        DemonTime = (TextView) findViewById(R.id.t1);

        MainFrame = (FrameLayout) findViewById(android.R.id.tabcontent);        //Todo
        MainFrame.setBackgroundResource(R.drawable.alpha_draw0);            //Todo
        transition = (TransitionDrawable) MainFrame.getBackground();        //Todo

        @SuppressLint("WrongViewCast")      //Todo
        WeatherView weatherView = findViewById(R.id.weather_view);      //Todo
//        weatherView.setEmissionRate(60f);       //떨어지는 개수의 정도     //Todo
        weatherView.setFadeOutPercent(10000f);         //입자 선명도         //Todo
        weatherView.setWeatherData(PrecipType.RAIN);        //날씨 설정         //Todo
        //PrecipType precip = PrecipType.CLEAR;         //맑음 설정하려면 setWeatherData에 이 값을 매개 변수로 전달       //Todo

        ImView = (ImageView) findViewById(R.id.ImView);     //Todo
        ImView.setTranslationX(1200);       //오른쪽 상단의 좌표. 만약 500이면 가운데임     Todo
        animationMoveAlpha = AnimationUtils.loadAnimation(this, R.anim.falling_star);       //Todo
        ImView.startAnimation(animationMoveAlpha);      //Todo
        Date dt = new Date();
        String CtSt = Ct.format(dt);
        CurrentTime = Integer.parseInt(CtSt);
        String Ctst1 = Ct1.format(dt);
        CurrentMinute = Integer.parseInt(Ctst1);
        CtSum = CtSt + Ctst1;
        System.out.println("-------------------------현재 시간 : "+CurrentTime);
        System.out.println("-------------------------현재 분 : "+CurrentMinute);


        ShowTimeMethod();
        init = initMainView.getInstance();
        init.initView(this);

        allData = LoadAllData.getInstance();

        svWidget = scrollViewinit.getInstance();

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT < 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        } else{
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //double longitude = location.getLongitude();
            //double latitude = location.getLatitude();
            double latitude = 37.6168305;
            double longitude = 127.105532;

            locationLatitude.setText("위도" + latitude);
            locationLongitude.setText("경도" + longitude);
            System.out.println(latitude);           //Todo
            System.out.println(longitude);          //Todo
            LocationLoadComplete = true;        //Todo 이 부분 바뀜

            allData.loadWeatherData(latitude, longitude, svWidget, init);
            allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this);
            allData.loadAirPollutionData(latitude, longitude, init);
            allData.loadGeoData(latitude, longitude, init);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }
    }
//
//    public String convertToLocal(String dt_txt) {
//        String localTime = "";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date dateUtcTime = dateFormat.parse(dt_txt);
//            int offset = 9; // offset 값은 로컬 시간대에 따라 다를 수 있습니다.
//            TimeZone timeZone = TimeZone.getTimeZone("GMT+" + offset);
//            Calendar calendar = Calendar.getInstance(timeZone);
//            calendar.setTimeInMillis(dateUtcTime.getTime());
//            Date dateLocalTime = calendar.getTime();
//            localTime = dateFormat.format(dateLocalTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return localTime;
//    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            String provider = location.getProvider();
//            double altitude = location.getAltitude();
            //double longitude = location.getLongitude();
            //double latitude = location.getLatitude();
            double latitude = 37.6168305;
            double longitude = 127.105532;
            allData.loadWeatherData(latitude, longitude, svWidget, init);
            allData.loadWeatherForecastData(latitude, longitude, svWidget, MainActivity.this);
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

    public void ShowTimeMethod(){
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date d = new Date();
                allDay = DateFormat.getDateTimeInstance().format(d);        //xxxx-xx-xx aMpM xx:xx:xx
                DemonTime.setText(dformat.format(d));
///////////////////////////////////////////////////////여기서부터///////////////////////////////////////////////////////////Todo
                TimeCount++;
                System.out.println(TimeCount);

                System.out.println("일출 : "+Opacity.sunrise);
                System.out.println("일몰 : "+Opacity.sunset);

                if(single == 0){
                    single = 1;
                    System.out.println("---------------------------------------처음 출력");
                    System.out.println("-------------------------------------------인덱스 : " + bgTest[11]);
                    System.out.println("----------------------------------------------분 : " + bgTest[10]);
                    System.out.println("---------------------------------------밀리초 계산 : " + bgTest[10] * 1000 * 60);
                    System.out.println("------------------------------------------초 계산 : " + bgTest[10] * 60);
                    MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                    transition = (TransitionDrawable) MainFrame.getBackground();
                    bgTmp = bgTest[10] * 60;
                    transition.startTransition(bgTmp);
//                    transition.startTransition(bgTest[10] * 60 * 10);
                    System.out.println("--------------------------------------현재 지속시간 : "+bgTest[10] * 60);
                    TimeCount = 0;
                    bgTmp = (bgTmp / 1000) + 1;
                    System.out.println("--------------------------------------------일몰 시간 : "+Integer.parseInt(Opacity.sunsetInt));
                    System.out.println("--------------------------------------------일몰 분 : "+Integer.parseInt(Opacity.sunsetInt1));
                    System.out.println("--------------------------------------------일몰 시간 + 분 : "+Integer.parseInt(Opacity.sunsetSum));
                    System.out.println("--------------------------------------------현재 시간 + 분 : "+Integer.parseInt(CtSum));
                } else if((bgTest != null) && (TimeCount == bgTmp)){
                    if(bgTmp1 == 9){
                        System.out.println("-----------------------------------bgTmp가 현재 9");
                        bgTmp = -1;
                        bgTmp1 = 0;
                        bgTest[11] = 8;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                    } else if(bgTest[11] == 8){
                        if((CurrentTime <= 23 && Integer.parseInt(CtSum) >= Integer.parseInt(Opacity.sunsetSum)) && single1 == 0){
                            single1++;
                            bgTest[11] = 8;
                            MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        } else {
                            bgTest[11] = 0;
                            MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        }
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 0){
                        bgTest[11] = 1;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 1){
                        bgTest[11] = 2;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 2){
                        bgTest[11] = 3;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 3){
                        bgTest[11] = 4;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 4) {
                        bgTest[11] = 5;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : " + bgTest[11]);
                    } else if(bgTest[11] == 5){
                        bgTest[11] = 6;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 6){
                        bgTest[11] = 7;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    } else if(bgTest[11] == 7){
                        bgTest[11] = 8;
                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
                    }
                    if(bgTmp == -1){
                        bgTmp = bgTmp + 1;
                    } else{
                        bgTmp = bgTest[11] + 1;
                    }
                    if(bgTmp == 9){
                        bgTmp1 = bgTmp;
                    }
                    System.out.println("------------------------------------------현재 bgTmp : "+bgTmp);
                    transition = (TransitionDrawable) MainFrame.getBackground();
                    transition.startTransition(bgTest[bgTmp] * 60);
                    System.out.println("------------------------------------------바뀐 지속시간 : "+bgTest[bgTmp] * 60);
                    bgTmp = ((bgTest[bgTmp] * 60) / 1000)+1;
                    TimeCount = 0;
                }

//                if(TimeCount <= 5) {
//                    if(single == 1){
//                        System.out.println("------------------------------------single = 1");
//                    }
//                    else {
//                        System.out.println("---------------------------------------처음 출력");
//                        System.out.println("-------------------------------------------인덱스 : " + bgTest[11]);
//                        System.out.println("----------------------------------------------분 : " + bgTest[10]);
//                        System.out.println("---------------------------------------밀리초 계산 : " + bgTest[10] * 1000 * 60);
//                        System.out.println("------------------------------------------초 계산 : " + bgTest[10] * 60);
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        transition = (TransitionDrawable) MainFrame.getBackground();
//                        bgTmp = bgTest[10] * 60;
//                        transition.startTransition(bgTmp * 10);
//                        System.out.println("--------------------------------------현재 지속시간 : "+bgTest[10] * 60);
//                        TimeCount = 0;
//                    }
//                    single = 1;
//                }
//
//                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Todo
//
//                else if(TimeCount == (bgTmp/100)+1){
//                    if(bgTest[11] == 8){
//                        bgTest[11] = 0;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 0){
//                        bgTest[11] = 1;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 1){
//                        bgTest[11] = 2;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 2){
//                        bgTest[11] = 3;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 3){
//                        bgTest[11] = 4;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 4) {
//                        bgTest[11] = 5;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : " + bgTest[11]);
//                    } else if(bgTest[11] == 5){
//                        bgTest[11] = 6;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 6){
//                        bgTest[11] = 7;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 7){
//                        bgTest[11] = 8;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    }
//                    bgTmp = bgTest[11] + 1;
//                    if(bgTmp == 10){
//                        bgTmp = 0;
//                    }
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(bgTest[bgTmp] * 60 * 10);
//                    System.out.println("------------------------------------------바뀐 지속시간 : "+bgTest[bgTest[11]+1] * 60);
//                    TimeCount = 0;
//                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Todo

//                if(TimeCount == (bgTest[0] * 60)/1000){
//                    if(bgTest[11] == 8){
//                        bgTest[11] = 0;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 0){
//                        bgTest[11] = 1;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 1){
//                        bgTest[11] = 2;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 2){
//                        bgTest[11] = 3;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 3){
//                        bgTest[11] = 4;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 4) {
//                        bgTest[11] = 5;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : " + bgTest[11]);
//                    } else if(bgTest[11] == 5){
//                        bgTest[11] = 6;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 6){
//                        bgTest[11] = 7;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    } else if(bgTest[11] == 7){
//                        bgTest[11] = 8;
//                        MainFrame.setBackgroundResource(alpha_draw[bgTest[11]]);
//                        System.out.println("---------------------------------------현재 인덱스 : "+bgTest[11]);
//                    }
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(bgTest[bgTest[11]+1] * 60);
//                    TimeCount = 0;
//                }
//                else if((TimeCount == tmpdurationValue * 60) && tmpbgIndex == 8){
//                    MainFrame.setBackgroundResource(alpha_draw[tmpbgIndex-8]);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(30 * 1000 * 60);
//                } else if((TimeCount == tmpdurationValue * 60) && tmpbgIndex == 0){
//                    MainFrame.setBackgroundResource(alpha_draw[tmpbgIndex++]);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(30 * 1000 * 60);
//                } else if((TimeCount == tmpdurationValue * 60) && tmpbgIndex == 1){
//                    MainFrame.setBackgroundResource(alpha_draw[tmpbgIndex++]);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(30 * 1000 * 60);
//                } else if((TimeCount == tmpdurationValue * 60) && tmpbgIndex == 2){
//                    MainFrame.setBackgroundResource(alpha_draw[tmpbgIndex++]);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(30 * 1000 * 60);
//                }

//                if(TimeCount % 5 == 0 && (LoadAllData.test[11] == 0 || LoadAllData.test[11] == 8))
//                    ImView.startAnimation(animationMoveAlpha);        //Todo

//                if(TimeCount == 1){
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw0);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 4) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw1);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 8) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw2);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 12) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw3);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 16) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw4);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 20) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw5);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 24) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw6);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 28) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw7);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                } else if(TimeCount == 32) {
//                    MainFrame.setBackgroundResource(R.drawable.alpha_draw8);
//                    transition = (TransitionDrawable) MainFrame.getBackground();
//                    transition.startTransition(3000);
//
//                    ImView.startAnimation(animationMoveAlpha);
//
//                }
//                if(TimeCount == 36){
//                    TimeCount = 0;
//                }
///////////////////////////////////////////////////////여기까지///////////////////////////////////////////////////////////Todo
                if(TimeCnt >= 10 && TimeCnt % 10 == 0){
                    System.out.println("토스트 실행");
                    Toast.makeText(getApplicationContext(),"인터넷 연결 또는 GPS연결을 확인해주세요.",Toast.LENGTH_LONG).show();
                }
            }
        };
        Runnable task = () -> {
            //noinspection InfiniteLoopStatement
            while(true){
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
                System.out.println("대기시간 : "+TimeCnt+"초");
                if(WeatherDataLoadComplete && WeatherForecastDataLoadComplete && LocationLoadComplete && AirPollutionDataLoadComplete
                        && GeoDataLoadComplete && LoadAllData.test != null){
                    bgTest = LoadAllData.test;
                    TimeCount = 0;
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

//    public static void BackInit(int test[]){
//        MainFrame.setBackgroundResource(alpha_draw[test[0]]);
//        transition = (TransitionDrawable) MainFrame.getBackground();
//        transition.startTransition(test[1] * 1000);
//
//        ImView.startAnimation(animationMoveAlpha);
//    }
}


//    private void loadWeatherData(double latitude, double longitude) {
//        Retrofit retroCurrentWeatherApi = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Retrofit retroGeoApi = new Retrofit.Builder()
//                .baseUrl("http://api.openweathermap.org/geo/1.0/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // retrofit 라이브러리 초기화
//        CWapi = retroCurrentWeatherApi.create(CWmapApi.class);
//        Geoapi = retroGeoApi.create(GeoMapApi.class);
//
//        Call<CurrentWeatherResponse> call = CWapi.getCurrentWeather(latitude, longitude, apiKey, units, apilang);
//        // 초기화한 라이브러리로 call 객체에 api JSON파일을 가져옴
//        call.enqueue(new Callback<CurrentWeatherResponse>() {
//            @Override
//            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
//                // api 호출 성공
//                if (response.isSuccessful()) {
//                    double temperature = response.body().getWeatherData().getTemperature();
//                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
//                    String temperatureString = String.format("%.1f", temperature);
//                    locationDescription.setText("현재 날씨는 " + descriptionString);
//                    locationLatitude.setText("위도" + latitude);
//                    locationLongitude.setText("경도" + longitude);
//                    temperatureTextView.setText(temperatureString + "℃");
//                    svWidget.TimeWeatherReload(MainActivity.this, scRollTimeWeather, temperatureString);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
//                // API 호출 실패
//                double temperature = -99;
//            }
//        });
//    }
//
//    private void loadWeatherForecastData(double latitude, double longitude){
//        Retrofit retroWFapi = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        WFapi = retroWFapi.create(WfMapApi.class);
//        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units);
//        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
//            @Override
//            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
//                // api 호출 성공
//                if (response.isSuccessful()) {
//                    double temp = response.body().getForecastList().get(0).getMain().getTemperature();
//                    testview.setText(String.format("%.1f", temp));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
//                // API 호출 실패
//                double temperature = -99;
//            }
//        });
//    }