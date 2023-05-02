package com.cookandroid.location_show;

import android.widget.TabHost;
import android.widget.TextView;

public class initMainView {
    private static initMainView Instance;

    private initMainView(){}

    public static initMainView getInstance(){
        if(Instance == null){
            synchronized (initMainView.class){
                Instance = new initMainView();
            }
        }
        return Instance;
    }

    public void initView(MainActivity mainActivity){
        TabHost tabHost = mainActivity.getTabHost();

        TabHost.TabSpec weatherNow = tabHost.newTabSpec("weatherNow").setIndicator("현재날씨");
        weatherNow.setContent(R.id.weatherNow);
        tabHost.addTab(weatherNow);

        TabHost.TabSpec foreCast = tabHost.newTabSpec("foreCast").setIndicator("일기예보");
        foreCast.setContent(R.id.foreCast);
        tabHost.addTab(foreCast);

        TabHost.TabSpec airPollution = tabHost.newTabSpec("airPollution").setIndicator("미세먼지");
        airPollution.setContent(R.id.airPollution);
        tabHost.addTab(airPollution);

        TabHost.TabSpec Etc = tabHost.newTabSpec("Etc").setIndicator("기타");
        Etc.setContent(R.id.Etc);
        tabHost.addTab(Etc);

        tabHost.setCurrentTab(0);
    }

}
