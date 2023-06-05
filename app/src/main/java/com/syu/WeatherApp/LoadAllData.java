/*
LoadAllData.java
Retrofit2 객체를 생성하고 api클래스를 호출하여 각각의 변수에 매핑하는 클래스 
api 호출에 실패하면 각 요소의 else문에서 response fail 시스템 메세지 출력

getInstance() - LoadAllData 객체를 초기화하고 할당
setApiKey(String _apiKey) - AndroidManifest.xml의 meta-data로 저장된 apikey를 불러와 변수에 할당
Load~Data() - 각각의 api를 호출하여 값 할당
checkRain() - api를 통해 불러온 객체로 비,눈이 오는지 안오는지 확인
straqi() - String AirQualityInformation 대기오염정도를 1~5로 리턴
getDirection() - 풍향을 String객체로 리턴
convertUnixTimeToKST() - UnixTime을 한국 표준 시간대로 변환
 */

package com.syu.WeatherApp;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.github.matteobattilana.weather.PrecipType;
import com.syu.WeatherApp.requestApi.APmapApi;
import com.syu.WeatherApp.requestApi.AirPollutionResponse;
import com.syu.WeatherApp.requestApi.CWmapApi;
import com.syu.WeatherApp.requestApi.CurrentWeatherResponse;
import com.syu.WeatherApp.requestApi.GeoMapApi;
import com.syu.WeatherApp.requestApi.GeoResponse;
import com.syu.WeatherApp.requestApi.WFmapApi;
import com.syu.WeatherApp.requestApi.WeatherForecastResponse;
import com.syu.WeatherApp.view.InitMainView;
import com.syu.WeatherApp.view.ScrollViewInit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadAllData {
    private String apiKey;
    private final String units = "metric";
    private final String apilang = "kr";
    private String currentWeatherIcon;

    private static LoadAllData Instance;
    public static int[] test;

    private LoadAllData(){}
    public static LoadAllData getInstance(){
        if(Instance == null){
            synchronized (LoadAllData.class){
                Instance = new LoadAllData();
            }
        }
        return Instance;
    }

    public void setApiKey(String _apiKey){
        apiKey=_apiKey;
    }

    public void loadWeatherData(double latitude, double longitude, ScrollViewInit svWidget, InitMainView imView) {
        Retrofit retroCurrentWeatherApi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // retrofit 라이브러리 초기화
        CWmapApi CWapi = retroCurrentWeatherApi.create(CWmapApi.class);

        Call<CurrentWeatherResponse> CWcall = CWapi.getCurrentWeather(latitude, longitude, apiKey, units, apilang);
        // 초기화한 라이브러리로 call 객체에 api JSON파일을 가져옴
        CWcall.enqueue(new Callback<CurrentWeatherResponse>() {     //enqueue 비동기식, execute 동기식
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherResponse> call, @NonNull Response<CurrentWeatherResponse> response) {
                //api 호출 성공
                if (response.isSuccessful()) {
                    CurrentWeatherResponse.WeatherData weatherData = response.body().getWeatherData();
                    double temperature = weatherData.getTemperature(); //Todo
                    double feelTemp =weatherData.getFeeltemp();
                    double humidity = weatherData.getHumidity();
                    double pressure = weatherData.getPressure();
                    double temp_min = weatherData.getTemp_min();
                    double temp_max = weatherData.getTemp_max();
                    double windSpeed = response.body().getWindData().getWindspeed();
                    double windDeg = response.body().getWindData().getWinddeg();
                    double clouds = response.body().getCloudData().getCloud();
                    double visibility = response.body().getVisibility();
                    Double rainAmount;
                    if(response.body() != null && response.body().getRaindata() != null){
                        rainAmount = response.body().getRaindata().getRainAmount();
                    }
                    else {
                        rainAmount = 0.0;
                    }
                    String sunrise = response.body().getSysdata().getSunrise();
                    String sunset = response.body().getSysdata().getSunset();
                    String sunsetString = convertUnixTimeToKST(sunset);
                    String sunriseString = convertUnixTimeToKST(sunrise);
                    Opacity opa = new Opacity(sunriseString, sunsetString);
                    try {
                        test = opa.setBackgroundImg();
                        System.out.println("test[11],bgindex = " + test[11]);       //bgindex
                        System.out.println("test[10],bgcurrentduration = " + test[10]);         //bgcurrentduration
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
                    currentWeatherIcon = response.body().getWeatherList().get(0).getIcon();

                    @SuppressLint("DefaultLocale") String temperatureString = String.format("%.1f", temperature);
                    @SuppressLint("DefaultLocale") String windSpeedString = String.format("%.1f", windSpeed);
                    @SuppressLint("DefaultLocale") String feelTempString = String.format("%.1f", feelTemp);
                    @SuppressLint("DefaultLocale") String humidityString = String.format("%.1f", humidity);
                    @SuppressLint("DefaultLocale") String visibilityString = String.format("%.1f", visibility);
                    @SuppressLint("DefaultLocale") String cloudsString = String.format("%.1f", clouds);
                    @SuppressLint("DefaultLocale") String pressureString = String.format("%.1f", pressure);
                    @SuppressLint("DefaultLocale") String rainAmountString = String.format("%.0f", rainAmount);

                    imView.setLocationDescription(descriptionString);
                    imView.setTemperatureTextView(temperatureString);
                    imView.setSunsetText(convertUnixTimeToKST(sunset));
                    imView.setSunriseText(convertUnixTimeToKST(sunrise));
                    imView.setWindSpeedText(windSpeedString);
                    imView.setWindDegText(getDirection(windDeg));
                    imView.setFeelTempText(feelTempString);
                    imView.setHumidityText(humidityString);
                    imView.setVisibilityText(visibilityString);
                    imView.setCloudsText(cloudsString);
                    imView.setPressureText(pressureString);
                    imView.setRainAmountText(rainAmountString);
                    svWidget.setPresentWeather(descriptionString);
                    svWidget.setCurrentIco(currentWeatherIcon);
                    svWidget.setPresentTemp(temperatureString);
                } else{
                    System.out.println("current weather Response Fail");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherResponse> call, @NonNull Throwable t) {
                // API 호출 실패
                imView.setTemperatureTextView("api 호출 실패");
                MainActivity.WeatherDataLoadComplete = false;
            }
        });
    }
    public void loadWeatherForecastData(double latitude, double longitude, ScrollViewInit svWidget, MainActivity mainActivity){
        Retrofit retroWFapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WFmapApi WFapi = retroWFapi.create(WFmapApi.class);
        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units, apilang);
        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecastResponse> call, @NonNull Response<WeatherForecastResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    double[] tempList = new double[40];
                    String[] icoList = new String[40];
                    String[] weatherList = new String[40];
                    List<WeatherForecastResponse.forecastList> forecastlist = response.body().getForecastList();
                    for(int i=0;i<40;i++){
                        tempList[i]= forecastlist.get(i).getMain().getTemperature();
                        icoList[i]= forecastlist.get(i).getWeatherList().get(0).getIcon();
                        weatherList[i] = forecastlist.get(i).getWeatherList().get(0).getDescription();
                    }
                    checkRain(icoList,mainActivity);
                    svWidget.setTempList(tempList);
                    svWidget.setIcoList(icoList);
                    svWidget.setWeatherList(weatherList);
                    svWidget.TimeWeatherReload(mainActivity, InitMainView.getscRollTimeWeather());
                    svWidget.DayWeatherReload(mainActivity, InitMainView.getscRollDayWeather());
                    svWidget.initView();
                }else{
                    System.out.println("forecast weather Response Fail");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastResponse> call, @NonNull Throwable t) {
                MainActivity.WeatherForecastDataLoadComplete = false;
                mainActivity.LoadingMotion();
                // API 호출 실패
//                test1.setText("api 호출 실패!!!"+ t.getMessage());
            }
        });
    }
    public void loadAirPollutionData(double latitude, double longitude, InitMainView imView){
        Retrofit retroAPapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APmapApi APapi = retroAPapi.create(APmapApi.class);
        Call<AirPollutionResponse> APcall = APapi.getAP(latitude, longitude, apiKey);
        APcall.enqueue(new Callback<AirPollutionResponse>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<AirPollutionResponse> call, @NonNull Response<AirPollutionResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    AirPollutionResponse.Air air = response.body().getAirList().get(0);
                    int aqi = air.getMain().getAqi();
                    double CO = air.getComp().getCo();
                    double O3 = air.getComp().getO3();
                    double pm10 = air.getComp().getPm10();
                    double pm2_5 = air.getComp().getPm2_5();
                    imView.setAirqualityText(straqi(aqi));
                    imView.setCOText(String.format("%.1fμg/m3",CO));
                    imView.setO3Text(String.format("%.1fμg/m3",O3));
                    imView.setPm10Text(String.format("%.1fμg/m3",pm10));
                    imView.setPm2_5Text(String.format("%.1fμg/m3",pm2_5));
                } else{
                    System.out.println("air pollution Response Fail");
                }
            }
            @Override
            public void onFailure(@NonNull Call<AirPollutionResponse> call, @NonNull Throwable t) {
                // API 호출 실패
                imView.setAirqualityText("api 호출 실패!!!"+ t.getMessage());
                imView.setCOText("api 호출 실패!!!"+ t.getMessage());
                imView.setO3Text("api 호출 실패!!!"+ t.getMessage());
                imView.setPm10Text("api 호출 실패!!!"+ t.getMessage());
                imView.setPm2_5Text("api 호출 실패!!!"+ t.getMessage());
                MainActivity.AirPollutionDataLoadComplete = false;
            }
        });
    }

    public void loadGeoData(double latitude, double longitude, InitMainView imView) {
        Retrofit retroGeoapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/geo/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GeoMapApi Geoapi = retroGeoapi.create(GeoMapApi.class);

        Call<List<GeoResponse>> Geocall = Geoapi.getReGeo(latitude, longitude, apiKey);
        Geocall.enqueue(new Callback<List<GeoResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GeoResponse>> call, @NonNull Response<List<GeoResponse>> response) {
                if (response.isSuccessful()) {
                    String koloc = response.body().get(0).getLocalName().getLocal_ko();
                    imView.setLocationText(koloc);
                }else{
                    System.out.println("geo Response Fail");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GeoResponse>> call, @NonNull Throwable t) {
                MainActivity.GeoDataLoadComplete = false;
            }
        });
    }

    private void checkRain(String[] weatherlist, MainActivity mainActivity) { // 0: 눈,비 없음, 1: 비, 2: 눈,  3: 눈,비
        Date curdate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dFormat = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(dFormat.format(curdate));
        int n = (24-currentHour)/3+1;
        boolean rain = false;
        boolean snow = false;
        if(currentWeatherIcon.equals("09d") || currentWeatherIcon.equals("10d") || currentWeatherIcon.equals("11d") || currentWeatherIcon.equals("09n") || currentWeatherIcon.equals("10n") || currentWeatherIcon.equals("11n")) {
            mainActivity.weatherView.setWeatherData(PrecipType.RAIN);
            InitMainView.setForecastText("현재 비가 오고 있습니다.");
            mainActivity.StarState = false;
            return;
        }
        if(currentWeatherIcon.equals("13d") || currentWeatherIcon.equals("13n")){
            mainActivity.weatherView.setWeatherData(PrecipType.SNOW);
            InitMainView.setForecastText("현재 눈이 오고 있습니다.");
            mainActivity.StarState = false;
            return;
        }
        if(currentWeatherIcon.equals("02d") || currentWeatherIcon.equals("03d") || currentWeatherIcon.equals("04d") || currentWeatherIcon.equals("50d") || currentWeatherIcon.equals("02n") || currentWeatherIcon.equals("03n") || currentWeatherIcon.equals("04n") || currentWeatherIcon.equals("50n")){
            mainActivity.weatherView.setWeatherData(PrecipType.CLEAR);
            mainActivity.StarState = false;
        }
        if(currentWeatherIcon.equals("01d") || currentWeatherIcon.equals("01n")){
            mainActivity.weatherView.setWeatherData(PrecipType.CLEAR);
            mainActivity.StarState = true;
        }
        for (int i = 0; i < n; i++) {
            if (weatherlist[i].equals("09d") || weatherlist[i].equals("10d") || weatherlist[i].equals("11d") || weatherlist[i].equals("09n") || weatherlist[i].equals("10n") || weatherlist[i].equals("11n")) {
                rain = true;
            }
            if (weatherlist[i].equals("13d") || weatherlist[i].equals("13n")) {
                snow = true;
            }
        }

        if (rain || snow) {
            if (rain && snow) {
                InitMainView.setForecastText("오늘 눈이나 비가 예상됩니다.");
            } else if (rain) {
                InitMainView.setForecastText("오늘 비가 예상됩니다.");
            } else {
                InitMainView.setForecastText("오늘 눈이 예상됩니다.");
            }
        } else {
            InitMainView.setForecastText("오늘 내에 눈이나 비가 예상되지 않습니다.");
        }
    }

    private String straqi(int aqi) {
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
    public String getDirection(double degree) {
        String direction;
        if (degree >= 22.5 && degree < 67.5) {
            direction = "북동풍";
        } else if (degree >= 67.5 && degree < 112.5) {
            direction = "동풍";
        } else if (degree >= 112.5 && degree < 157.5) {
            direction = "남동풍";
        } else if (degree >= 157.5 && degree < 202.5) {
            direction = "남풍";
        } else if (degree >= 202.5 && degree < 247.5) {
            direction = "남서풍";
        } else if (degree >= 247.5 && degree < 292.5) {
            direction = "서풍";
        } else if (degree >= 292.5 && degree < 337.5) {
            direction = "북서풍";
        } else {
            direction = "북풍";
        }
        return direction;
    }

    public static String convertUnixTimeToKST(String unixTimeString) {
        long unixTime = 0;
        try {
            unixTime = Long.parseLong(unixTimeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // UNIX 시간을 밀리초 단위로 변환
        long unixTimeMillis = unixTime * 1000L;

        // KST 시간대로 설정
        TimeZone kstTimeZone = TimeZone.getTimeZone("Asia/Seoul");

        // SimpleDateFormat을 사용하여 KST 시간 형식으로 변환
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분");
        dateFormat.setTimeZone(kstTimeZone);

        // KST 시간으로 변환
        return dateFormat.format(new Date(unixTimeMillis));
    }
}