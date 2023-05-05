package com.cookandroid.location_show;

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

public class LoadAllData {
    private String apiKey = "163034e395f4430a30d20336ccc67b22";
    private String units = "metric";
    private String apilang = "kr";

    private static LoadAllData Instance;

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
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    double temperature = response.body().getWeatherData().getTemperature();
                    String descriptionString = response.body().getWeatherList().get(0).getDescription();
                    String icon = response.body().getWeatherList().get(0).getIcon();
                    String temperatureString = String.format("%.1f", temperature);
                    imView.setLocationDescription(descriptionString);
                    imView.setTemperatureTextView(temperatureString);
                    svWidget.setPresentWeather(descriptionString);
                    svWidget.setCurrentIco(icon);
                    svWidget.setPresentTemp(temperatureString);
                    double feelTemp = response.body().getWeatherData().getFeeltemp();
                    double humidity = response.body().getWeatherData().getHumidity();
                    double pressure = response.body().getWeatherData().getPressure();
                    double temp_min = response.body().getWeatherData().getTemp_min();
                    double temp_max = response.body().getWeatherData().getTemp_max();
                    double windSpeed = response.body().getWindData().getWindspeed();
                    double windDeg = response.body().getWindData().getWinddeg();
                    double clouds = response.body().getCloudData().getCloud();
                    String sunrise = response.body().getSysdata().getSunrise();
                    String sunset = response.body().getSysdata().getSunset();
                } else{
                    System.out.println("Response Fail");
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                // API 호출 실패
                imView.setTemperatureTextView("api 호출 실패");
            }
        });
    }
    public void loadWeatherForecastData(double latitude, double longitude, scrollViewinit svWidget, MainActivity mainActivity, initMainView imView){
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
                    String iconUrl = response.body().getForecastList().get(0).getWeatherList().get(0).getIcon();
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
                    svWidget.TimeWeatherReload(mainActivity, imView.getscRollTimeWeather());
                    svWidget.DayWeatherReload(mainActivity, imView.getscRollDayWeather());
                    svWidget.initView();
                    //test1.setText(String.format("%.1f'C", temp));

                }
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
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
            @Override
            public void onResponse(Call<AirPollutionResponse> call, Response<AirPollutionResponse> response) {
                // api 호출 성공
                if (response.isSuccessful()) {
                    int aqi = response.body().getAirList().get(0).getMain().getAqi();
                    double CO = response.body().getAirList().get(0).getComp().getCo();
                    double O3 = response.body().getAirList().get(0).getComp().getO3();
                    double pm10 = response.body().getAirList().get(0).getComp().getPm10();
                    double pm2_5 = response.body().getAirList().get(0).getComp().getPm2_5();
                    imView.setAirqualityText(straqi(aqi));
                    imView.setCOText(String.format("%.2fμg/m3",CO));
                    imView.setO3Text(String.format("%.2fμg/m3",O3));
                    imView.setPm10Text(String.format("%.2fμg/m3",pm10));
                    imView.setPm2_5Text(String.format("%.2fμg/m3",pm2_5));
//                    test1.setText(Integer.toString(aqi));

                }
            }
            @Override
            public void onFailure(Call<AirPollutionResponse> call, Throwable t) {
                // API 호출 실패
                imView.setAirqualityText("api 호출 실패!!!"+ t.getMessage());
                imView.setCOText("api 호출 실패!!!"+ t.getMessage());
                imView.setO3Text("api 호출 실패!!!"+ t.getMessage());
                imView.setPm10Text("api 호출 실패!!!"+ t.getMessage());
                imView.setPm2_5Text("api 호출 실패!!!"+ t.getMessage());
            }
        });
    }

    public void loadGeoData(double latitude, double longitude, initMainView imView) {
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
                    imView.setLocationText(koloc);
                }
            }

            @Override
            public void onFailure(Call<List<GeoResponse>> call, Throwable t) {

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
            direction = "북동";
        } else if (degree >= 67.5 && degree < 112.5) {
            direction = "동";
        } else if (degree >= 112.5 && degree < 157.5) {
            direction = "남동";
        } else if (degree >= 157.5 && degree < 202.5) {
            direction = "남";
        } else if (degree >= 202.5 && degree < 247.5) {
            direction = "남서";
        } else if (degree >= 247.5 && degree < 292.5) {
            direction = "서";
        } else if (degree >= 292.5 && degree < 337.5) {
            direction = "북서";
        } else {
            direction = "북";
        }
        return direction;
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
}
