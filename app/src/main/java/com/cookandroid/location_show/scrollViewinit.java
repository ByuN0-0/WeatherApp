package com.cookandroid.location_show;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class scrollViewinit {
    private static scrollViewinit Instance;
    String presentTime, presentDay, presentWeather;
    LinearLayout[] TimeWeather_Lay, TimeWeather_InsideLay, DayWeather_Lay, DayWeather_InsideLay;
    TextView[] TimeWeather, DayWeather;
    String aMpM;
    String presentTemp = "";
    String currentIco = "";
    private static String[] weatherList;
    private static double[] tempList;
    private static String[] icoList;
    int DayOfWeekIndex;
    String[] DayOfWeek = {"","일","월","화","수","목","금","토"};

    private scrollViewinit(){}
    public void setPresentWeather(String pweather){
        presentWeather = pweather;
    }
    public void setPresentTemp(String temp){
        presentTemp = temp;
    }
    public void setTempList(double[] templist){
        tempList=templist;
    }
    public void setIcoList(String[] icolist){
        icoList=icolist;
    }
    public void setCurrentIco(String icon) {
        currentIco = icon;
    }
    public void setWeatherList(String[] weatherlist){
        weatherList = weatherlist;
    }

    public static scrollViewinit getInstance(){
        if(Instance == null){
            synchronized (scrollViewinit.class){
                if(weatherList == null && tempList == null && icoList == null){
                    weatherList = new String[40];
                    tempList = new double[40];
                    icoList = new String[40];
                }
                Instance = new scrollViewinit();
            }
        }
        return Instance;
    }

    public void initView(){
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+MainActivity.allDay);
        System.out.println("1 : "+presentWeather);
        System.out.println("2 : "+presentTemp);
        System.out.println("3 : "+currentIco);
        System.out.println("4 : "+ Arrays.toString(tempList));
        System.out.println("5 : "+ Arrays.toString(icoList));
        System.out.println("6 : "+ Arrays.toString(weatherList));
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }


/////////////////////////////////////안써도됨//////////////////////////////////////////
//    public void addView(MainActivity mainActivity, LinearLayout scRoll, int index){
//        if(index == 0){
//            TimeWeatherWidgetInit(mainActivity);
//            for(LinearLayout L : TimeWeather_Lay)
//                scRoll.addView(L);
//        } else if(index == 1){
//            DayWeatherWidgetInit(mainActivity);
//            for(LinearLayout L : DayWeather_Lay)
//                scRoll.addView(L);
//            //Todo
//        }
//    }
//////////////////////////////////////////////////////////////////////////////////////



    @SuppressLint({"SimpleDateFormat", "SetTextI18n", "RtlHardcoded"})
    private void DayWeatherWidgetInit(MainActivity mainActivity){
        presentDay = new SimpleDateFormat("E").format(new Date());       //일주일 중 무슨 요일인지 또는 일주일 중 몇 번째 요일인지 또는 한달 중 몇일인지 포맷 변경 가능
        DayWeather_Lay = new LinearLayout[10];
        DayWeather_InsideLay = new LinearLayout[4];
        DayWeather = new TextView[4];
        Calendar cal = Calendar.getInstance();
        DayOfWeekIndex = cal.get(Calendar.DAY_OF_WEEK);
        double DOWI = DayOfWeekIndex;
        if (aMpM.equals("오후")){ DOWI += 0.6; }

        for(int i = 0; i < DayWeather_Lay.length; i++){
            DayWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
            for(int j = 0; j < DayWeather_InsideLay.length; j++){
                DayWeather_InsideLay[j] = new LinearLayout(mainActivity.getApplicationContext());
                DayWeather[j] = new TextView(mainActivity.getApplicationContext());
                if(j == 0){
                    DayWeather[j].setText(DayOfWeek[(int)DOWI]+"요일");
                    DOWI += 0.5;
                    if(DOWI>7.7){ DOWI=1; }
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#ff0000"));
                    DayWeather[j].setTextSize(20);
                    DayWeather[j].setTextColor(Color.parseColor("#ffffff"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.height = 200;
                    LPS.setMargins(20, 0, 0, 0);
                    DayWeather[j].setLayoutParams(LPS);
//                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                    LPS1.weight = 1;
//                    LPS1.setMargins(0,0,3,3);
                    LPS1.setMargins(0,0,0,10);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER_VERTICAL);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#99ccff"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round_left);

                } else if(j == 1){
                    DayWeather[j].setText("image");
//                    DayWeather[j].setBackgroundResource(R.drawable.noimage);
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#00ff00"));
                    if(i==0){ loadImage(DayWeather[j], currentIco); }
                    else{ loadImage(DayWeather[j], icoList[i*4]); }
                    DayWeather[j].setTextSize(20);
                    DayWeather[j].setTextColor(Color.parseColor("#000000"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LPS.setMargins(20,20,20,20);
//                    LPS.height = 200;
                    DayWeather[j].setLayoutParams(LPS);
//                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                    LPS1.weight = 1;
//                    LPS1.setMargins(0,0,3,3);
                    LPS1.setMargins(0,0,0,10);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#808080"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.square);

                } else if(j == 2){
                    DayWeather[j].setText("날씨");
                    if(i==0){ DayWeather[j].setText(presentWeather); }
                    else{ DayWeather[j].setText(weatherList[i*4]); }
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#964b00"));
                    DayWeather[j].setTextSize(20);
                    DayWeather[j].setTextColor(Color.parseColor("#ffffff"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.setMargins(180,0,50,0);
//                    LPS.height = 200;
                    DayWeather[j].setLayoutParams(LPS);
//                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                    LPS1.weight = 2;
//                    LPS1.height = LinearLayout.LayoutParams.MATCH_PARENT;
//                    LPS1.width = 400;
//                    LPS1.setMargins(0,0,3,3);
                    LPS1.setMargins(0,0,0,10);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#d09aff"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.square);

                } else if(j == 3){
                    DayWeather[j].setText("℃");
                    if(i==0){ DayWeather[j].setText(presentTemp+"℃"); }
                    else{
                        @SuppressLint("DefaultLocale")
                        String tmp = String.format("%.1f",tempList[i*4]);
                        DayWeather[j].setText(tmp+"℃");
                    }
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#0000ff"));
                    DayWeather[j].setTextSize(20);
                    DayWeather[j].setTextColor(Color.parseColor("#ffffff"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                    LPS.setMargins(0,0,30,0);
//                    LPS.height = 200;
                    DayWeather[j].setLayoutParams(LPS);
//                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                    LPS1.weight = 1;
                    LPS1.setMargins(0,0,0,10);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#ff7f00"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round_right);

                }
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                LPS.width = LinearLayout.LayoutParams.MATCH_PARENT;
                LPS.height = 200;
                DayWeather_Lay[i].setWeightSum(5);
                DayWeather_Lay[i].setLayoutParams(LPS);
                DayWeather_Lay[i].addView(DayWeather_InsideLay[j]);
                DayWeather_Lay[i].setGravity(Gravity.CENTER_VERTICAL);
//                DayWeather_Lay[i].setBackgroundColor(Color.parseColor("#000000"));
                MainActivity.WeatherForecastDataLoadComplete=true;      //Todo 이 부분 바뀜
//                DayWeather_Lay[i].setBackgroundResource(R.drawable.round);
            }
        }

//        DayWeather_Lay[0].setBackgroundColor(Color.parseColor("#ff5050"));
//        DayWeather_Lay[1].setBackgroundColor(Color.parseColor("#f0f000"));
//        DayWeather_Lay[2].setBackgroundColor(Color.parseColor("#f0505f"));
//        DayWeather_Lay[3].setBackgroundColor(Color.parseColor("#0ff000"));
//        DayWeather_Lay[4].setBackgroundColor(Color.parseColor("#0f4f10"));
//        DayWeather_Lay[5].setBackgroundColor(Color.parseColor("#1f3525"));
//        DayWeather_Lay[6].setBackgroundColor(Color.parseColor("#31f455"));
//        DayWeather_Lay[7].setBackgroundColor(Color.parseColor("#ffffff"));
//        DayWeather_Lay[8].setBackgroundColor(Color.parseColor("#f0f00f"));
//        DayWeather_Lay[9].setBackgroundColor(Color.parseColor("#f0467f"));

    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void TimeWeatherWidgetInit(MainActivity mainActivity){
        presentTime = new SimpleDateFormat("HH").format(new Date());
        TimeWeather = new TextView[3];
        TimeWeather_InsideLay = new LinearLayout[3];
        TimeWeather_Lay = new LinearLayout[9];
        int tmp1 = 0;
        int tmp2 = 0;
        for(int i = 0; i < TimeWeather_Lay.length; i++){
            TimeWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
            final int crt = Integer.parseInt(presentTime);
            if(i == 0) {
                tmp1 = crt;
                if(tmp1<12 && tmp1>=0){
                    aMpM = "오전";
                    tmp2 = tmp1;
                } else{
                    aMpM = "오후";
                }
                if(aMpM.equals("오후")) {
                    if(tmp1 == 12) tmp2 = tmp1;
                    else {
                        tmp2 = Math.abs(24 - tmp1 - 12);
                    }
                }
            }
            else {
                int tmp3 = -1;
                tmp1 = tmp1 + 3;
                if(tmp1 >= 24) {
                    tmp3 = Math.abs(24 - tmp1);
                }
                if(tmp1<12 && tmp1>=0){
                    aMpM = "오전";
                    tmp2 = tmp1;
                } else{
                    aMpM = "오후";
                    if(tmp1 == 12){
                        tmp2 = tmp1;
                    } else{
                        tmp2 = Math.abs(24 - tmp1 - 12);
                    }
                }

                if(tmp3<12 && tmp3>=0){
                    aMpM = "오전";
                    tmp2 = tmp3;
                } else if(tmp3 >= 12 && tmp3 < 24){
                    aMpM = "오후";
                    if(tmp3 == 12){
                        tmp2 = tmp3;
                    }else{
                        tmp2 = Math.abs(24 - tmp3 - 12);
                    }
                }
            }
            for(int j = 0; j < TimeWeather_InsideLay.length; j++){
                TimeWeather_InsideLay[j] = new LinearLayout(mainActivity.getApplicationContext());
                TimeWeather[j] = new TextView(mainActivity.getApplicationContext());
                if(j == 0){
                    TimeWeather[j].setText(aMpM+(tmp2)+"시");
                    TimeWeather[j].setTextColor(Color.parseColor("#ffffff"));
//                    TimeWeather[j].setBackgroundColor(Color.parseColor("#000000"));
//                    TimeWeather[j].setPadding(5,5,5,5);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                    TimeWeather[j].setLayoutParams(LPS);

                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,0);        //높이, 너비
                    LPS1.weight = 1;
                    TimeWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    TimeWeather_InsideLay[j].setLayoutParams(LPS1);
                    TimeWeather_InsideLay[j].addView(TimeWeather[j]);
//                    TimeWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#ff0000"));
//                    TimeWeather_InsideLay[j].setBackgroundResource(R.drawable.round_onlyborder_top);
                }

                else if(j == 1){
                    TimeWeather[j].setText("image");
//                    TimeWeather[j].setTextColor(Color.parseColor("#000000"));
//                    TimeWeather[j].setBackgroundResource(R.drawable.noimage);
                    if(i==0){ loadImage(TimeWeather[j], currentIco); }
                    else{ loadImage(TimeWeather[j], icoList[i-1]); }
                    TimeWeather[j].setGravity(Gravity.CENTER);
//                    TimeWeather[j].setPadding(5,5,5,5);
                    LinearLayout.LayoutParams LPS2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    TimeWeather[j].setLayoutParams(LPS2);

                    TimeWeather_InsideLay[j] = new LinearLayout(mainActivity.getApplicationContext());
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,0);        //높이, 너비
                    LPS1.weight = 2;
                    TimeWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    TimeWeather_InsideLay[j].setLayoutParams(LPS1);
                    TimeWeather_InsideLay[j].addView(TimeWeather[j]);
//                    TimeWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#00ff00"));
//                    TimeWeather_InsideLay[j].setBackgroundResource(R.drawable.square_onlyborder);
                }

                else if(j == 2){
                    if (i==0) { TimeWeather[j].setText(presentTemp+"℃"); }
                    else{
                        @SuppressLint("DefaultLocale")
                        String tmp = String.format("%.1f",tempList[i-1]);
                        TimeWeather[j].setText(tmp+"℃");
                    }
                    TimeWeather[j].setTextColor(Color.parseColor("#ffffff"));
//                    TimeWeather[j].setBackgroundColor(Color.parseColor("#000000"));
//                    TimeWeather[j].setPadding(5,5,5,5);
                    LinearLayout.LayoutParams LPS3 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
                    TimeWeather[j].setLayoutParams(LPS3);

                    TimeWeather_InsideLay[j] = new LinearLayout(mainActivity.getApplicationContext());
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,0);        //높이, 너비
                    LPS1.weight = 1;
                    TimeWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    TimeWeather_InsideLay[j].setLayoutParams(LPS1);
                    TimeWeather_InsideLay[j].addView(TimeWeather[j]);
//                    TimeWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#0000ff"));
//                    TimeWeather_InsideLay[j].setBackgroundResource(R.drawable.round_onlyborder_bottom);
                }


                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                LPS.width = 200;
                LPS.height = LinearLayout.LayoutParams.MATCH_PARENT;
                LPS.setMargins(0,0,5,0);
                TimeWeather_Lay[i].setWeightSum(4);
                TimeWeather_Lay[i].setLayoutParams(LPS);
//                TimeWeather_Lay[i].setBackgroundColor(Color.parseColor("#ff00ff"));
//            TimeWeather_Lay[i].setBackgroundResource(R.drawable.noimage);
                TimeWeather_Lay[i].addView(TimeWeather_InsideLay[j]);
                TimeWeather_Lay[i].setOrientation(LinearLayout.VERTICAL);
                MainActivity.WeatherDataLoadComplete = true;        //Todo 이 부분 바뀜
            }
        }
    }

    public void TimeWeatherReload(MainActivity mainActivity, LinearLayout scRoll){
        scRoll.removeAllViews();
        TimeWeatherWidgetInit(mainActivity);
        for(LinearLayout L : TimeWeather_Lay)
            scRoll.addView(L);
    }

    public void DayWeatherReload(MainActivity mainActivity, LinearLayout scRoll){
        scRoll.removeAllViews();
        DayWeatherWidgetInit(mainActivity);
        for(LinearLayout L : DayWeather_Lay)
            scRoll.addView(L);
    }
    public void loadImage(TextView tv, String iconUrl){
        String icon = "a"+iconUrl;
        int resourceId = tv.getResources().getIdentifier(icon, "drawable", tv.getContext().getPackageName());
        tv.setText("");
        tv.setBackgroundResource(resourceId);
//        String url = "https://openweathermap.org/img/w/"+iconUrl+".png";
//        tv.setText("");
//        Glide.with(tv.getContext()).load(url).into(new CustomTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                tv.setBackground(resource);
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//        });
        tv.setWidth(150);
        tv.setHeight(150);
    }
}


//////////////////////////////////////////////////DayWeatherWidgetInit백업/////////////////////////////////////////////////////////



//        for(int i = 0; i < DayWeather_Lay.length; i++){
//            DayWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
//            for(int j = 0; j < DayWeather.length; j++){
//                DayWeather[j] = new TextView(mainActivity.getApplicationContext());
//                if(j == 0){
//                    DayWeather[j].setText(presentDay+"요일");
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#ff0000"));
//                    DayWeather[j].setTextSize(20);
////                    DayWeather[j].setGravity(Gravity.CENTER);
//                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
////                    LPS.height = 200;
//                    LPS.setMargins(0, 0, 0, 0);
//                    DayWeather[j].setLayoutParams(LPS);
//
//                } else if(j == 1){
//                    DayWeather[j].setText("image");
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#00ff00"));
////                    DayWeather[j].setGravity(Gravity.CENTER);
//                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.setMargins(50,0,50,0);
////                    LPS.height = 200;
//
//                    DayWeather[j].setLayoutParams(LPS);
//
//                } else if(j == 2){
//                    DayWeather[j].setText("날씨");
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#964b00"));
////                    DayWeather[j].setGravity(Gravity.CENTER);
//                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.setMargins(180,0,50,0);
////                    LPS.height = 200;
//                    DayWeather[j].setLayoutParams(LPS);
//
//                } else if(j == 3){
//                    DayWeather[j].setText(Weather+"℃");
//                    DayWeather[j].setBackgroundColor(Color.parseColor("#0000ff"));
////                    DayWeather[j].setGravity(Gravity.CENTER);
//                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.setMargins(100,0,0,0);
////                    LPS.height = 200;
//                    DayWeather[j].setLayoutParams(LPS);
//                }
//                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
//                LPS.width = LinearLayout.LayoutParams.MATCH_PARENT;
//                LPS.height = 200;
//                DayWeather_Lay[i].setLayoutParams(LPS);
//                DayWeather_Lay[i].addView(DayWeather[j]);
//                DayWeather_Lay[i].setGravity(Gravity.CENTER_VERTICAL);
//            }
//
//
////            DayWeather[i] = new TextView(mainActivity.getApplicationContext());
////            DayWeather[i].setText(Weather+"℃");
////            DayWeather[i].setTextColor(Color.parseColor("#000000"));
////            DayWeather[i].setGravity(Gravity.CENTER);
////            DayWeather[i].setBackgroundResource(R.drawable.noimage);
////            DayWeather[i].setPadding(5,5,5,5);
//        }
//
//        DayWeather_Lay[0].setBackgroundColor(Color.parseColor("#ff5050"));
//        DayWeather_Lay[1].setBackgroundColor(Color.parseColor("#f0f000"));
//        DayWeather_Lay[2].setBackgroundColor(Color.parseColor("#f0505f"));
//        DayWeather_Lay[3].setBackgroundColor(Color.parseColor("#0ff000"));
//        DayWeather_Lay[4].setBackgroundColor(Color.parseColor("#0f4f10"));
//        DayWeather_Lay[5].setBackgroundColor(Color.parseColor("#1f3525"));
//        DayWeather_Lay[6].setBackgroundColor(Color.parseColor("#31f455"));
//        DayWeather_Lay[7].setBackgroundColor(Color.parseColor("#ffffff"));
//        DayWeather_Lay[8].setBackgroundColor(Color.parseColor("#f0f00f"));
//        DayWeather_Lay[9].setBackgroundColor(Color.parseColor("#f0467f"));
//        return DayWeather_Lay;


////////////////////////////////////////////////////////TimeWeather백업///////////////////////////////////////////////////////////////////////////
//            TimeWeather[i] = new TextView(mainActivity.getApplicationContext());
//            TimeWeather[i].setText(aMpM+(tmp2)+"시"+mainActivity.getString(R.string.lineSpace)+presentWeather+"℃");
//            TimeWeather[i].setTextColor(Color.parseColor("#ffffff"));
//            TimeWeather[i].setGravity(Gravity.CENTER|Gravity.TOP);
//            TimeWeather[i].setBackgroundResource(R.drawable.noimage|R.drawable.round_onlyborder);
////            TimeWeather[i].setBackgroundColor(Color.parseColor("#ff0000"));
//            TimeWeather[i].setPadding(5,5,5,5);
//
//            TimeWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
//            LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
//                    0, 0);        //높이, 너비
//            LPS.setMargins(0,0,3,0);
//            LPS.weight = 1;
//            TimeWeather_Lay[i].setOrientation(LinearLayout.VERTICAL);
//            TimeWeather_Lay[i].setWeightSum(4);
//            TimeWeather_Lay[i].setLayoutParams(LPS);
////            TimeWeather_Lay[i].setBackgroundResource(R.drawable.noimage);
//            TimeWeather_Lay[i].addView(TimeWeather_InsideLay);