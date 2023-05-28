package com.cookandroid.location_show;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

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
    private final String apiKey = "163034e395f4430a30d20336ccc67b22";
    private final String units = "metric";
    private final String apilang = "kr";

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

    public void loadWeatherData(double latitude, double longitude, scrollViewinit svWidget, initMainView imView) {
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
//                    double temperature = response.body().getWeatherData().getTemperature();
//                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
//                    String icon = response.body().getWeatherList().get(0).getIcon();
//                    @SuppressLint("DefaultLocale") String temperatureString = String.format("%.1f", temperature);
//                    imView.setLocationDescription(descriptionString);
//                    imView.setTemperatureTextView(temperatureString);
//                    svWidget.setPresentWeather(descriptionString);
//                    svWidget.setCurrentIco(icon);
//                    svWidget.setPresentTemp(temperatureString);
//                } else{
//                    System.out.println("Response Fail");
//                }
                    double temperature = response.body().getWeatherData().getTemperature(); //Todo
                    double feelTemp = response.body().getWeatherData().getFeeltemp();
                    double humidity = response.body().getWeatherData().getHumidity();
                    double pressure = response.body().getWeatherData().getPressure();
                    double temp_min = response.body().getWeatherData().getTemp_min();
                    double temp_max = response.body().getWeatherData().getTemp_max();
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
                        System.out.println("test[11] = " + test[11]);       //bgindex
                        System.out.println("test[0] = " + test[10]);         //bgcurrentduration
//                        MainActivity.BackInit(test);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
                    String icon = response.body().getWeatherList().get(0).getIcon();

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
                    svWidget.setCurrentIco(icon);
                    svWidget.setPresentTemp(temperatureString);
                } else{
                    System.out.println("Response Fail");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherResponse> call, @NonNull Throwable t) {
                // API 호출 실패
                imView.setTemperatureTextView("api 호출 실패");
                MainActivity.WeatherDataLoadComplete = false;       //Todo 이 부분 바뀜
            }
        });
    }
    public void loadWeatherForecastData(double latitude, double longitude, scrollViewinit svWidget, MainActivity mainActivity){
        Retrofit retroWFapi = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WfMapApi WFapi = retroWFapi.create(WfMapApi.class);
        Call<WeatherForecastResponse> WFcall = WFapi.getWf(latitude, longitude, apiKey, units, apilang);
        WFcall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecastResponse> call, @NonNull Response<WeatherForecastResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
//                    String iconUrl = response.body().getForecastList().get(0).getWeatherList().get(0).getIcon();
//                    loadImage(test2, iconUrl);
                    double[] tempList = new double[40];
                    String[] icoList = new String[40];
                    String[] weatherList = new String[40];
                    for(int i=0;i<40;i++){
                        tempList[i]= response.body().getForecastList().get(i).getMain().getTemperature();
                        icoList[i]= response.body().getForecastList().get(i).getWeatherList().get(0).getIcon();
                        weatherList[i] = response.body().getForecastList().get(i).getWeatherList().get(0).getDescription();
                    }
//                    for(int i=0;i<40;i++){
//                        icoList[i]= response.body().getForecastList().get(i).getWeatherList().get(0).getIcon();
//                    }
                    checkRain(icoList);
                    svWidget.setTempList(tempList);
                    svWidget.setIcoList(icoList);
                    svWidget.setWeatherList(weatherList);
                    svWidget.TimeWeatherReload(mainActivity, initMainView.getscRollTimeWeather());
                    svWidget.DayWeatherReload(mainActivity, initMainView.getscRollDayWeather());
                    svWidget.initView();
//                    mainActivity.TM.cancel();   //Todo 이 부분 바뀜
//                    mainActivity.PD.dismiss();  //Todo 이 부분 바뀜
                    //test1.setText(String.format("%.1f'C", temp));
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastResponse> call, @NonNull Throwable t) {
                MainActivity.WeatherForecastDataLoadComplete = false;       //Todo 이 부분 바뀜
                mainActivity.LoadingMotion();
                // API 호출 실패
//                test1.setText("api 호출 실패!!!"+ t.getMessage());
            }
        });
    }
    public void loadAirPollutionData(double latitude, double longitude, initMainView imView){
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
                    int aqi = response.body().getAirList().get(0).getMain().getAqi();
                    double CO = response.body().getAirList().get(0).getComp().getCo();
                    double O3 = response.body().getAirList().get(0).getComp().getO3();
                    double pm10 = response.body().getAirList().get(0).getComp().getPm10();
                    double pm2_5 = response.body().getAirList().get(0).getComp().getPm2_5();
                    imView.setAirqualityText(straqi(aqi));
                    imView.setCOText(String.format("%.1fμg/m3",CO));
                    imView.setO3Text(String.format("%.1fμg/m3",O3));
                    imView.setPm10Text(String.format("%.1fμg/m3",pm10));
                    imView.setPm2_5Text(String.format("%.1fμg/m3",pm2_5));
//                    test1.setText(Integer.toString(aqi));

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
                MainActivity.AirPollutionDataLoadComplete = false;       //Todo 이 부분 바뀜
            }
        });
    }

    public void loadGeoData(double latitude, double longitude, initMainView imView) {
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GeoResponse>> call, @NonNull Throwable t) {
                MainActivity.GeoDataLoadComplete = false;       //Todo 이 부분 바뀜
            }
        });
    }

    private void checkRain(String[] weatherlist) { // 0: 눈,비 없음, 1: 비, 2: 눈,  3: 눈,비
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
//                forecastText.setText("오늘 한때 눈이나 비가 예상됩니다.");
                initMainView.setForecastText("오늘 한때 눈이나 비가 예상됩니다.",0);
            } else if (rain) {
//                forecastText.setText("오늘 한때 비가 예상됩니다.");
                initMainView.setForecastText("오늘 한때 비가 예상됩니다.",1);
            } else {
//                forecastText.setText("오늘 한때 눈이 예상됩니다.");
                initMainView.setForecastText("오늘 한때 눈이 예상됩니다.",2);
            }
        } else {
//            forecastText.setText("오늘은 눈이나 비가 예상되지 않습니다.");
            initMainView.setForecastText("오늘은 눈이나 비가 예상되지 않습니다.",3);
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
