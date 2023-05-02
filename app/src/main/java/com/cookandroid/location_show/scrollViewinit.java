package com.cookandroid.location_show;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class scrollViewinit {
    private static scrollViewinit Instance;
    String presentTime, presentDay;
    LinearLayout[] TimeWeather_Lay, DayWeather_Lay, DayWeather_InsideLay;
    TextView[] TimeWeather, DayWeather;
    String aMpM;
    int DayOfWeekIndex;
    String[] DayOfWeek = {"일","월","화","수","목","금","토"};

    private scrollViewinit(){}

    public static scrollViewinit getInstance(){
        if(Instance == null){
            synchronized (scrollViewinit.class){
                Instance = new scrollViewinit();
            }
        }
        return Instance;
    }

    public void addView(MainActivity mainActivity, LinearLayout scRoll, int index){
        if(index == 0){
            TimeWeatherWidgetInit(mainActivity, "");
            for(LinearLayout L : TimeWeather_Lay)
                scRoll.addView(L);
        } else if(index == 1){
            DayWeatherWidgetInit(mainActivity, "");
            for(LinearLayout L : DayWeather_Lay)
                scRoll.addView(L);
            //Todo
        }
    }

    private LinearLayout[] DayWeatherWidgetInit(MainActivity mainActivity, String Weather){
        presentDay = new SimpleDateFormat("E").format(new Date());       //일주일 중 무슨 요일인지 또는 일주일 중 몇 번째 요일인지 또는 한달 중 몇일인지 포맷 변경 가능
        DayWeather_Lay = new LinearLayout[10];
        DayWeather_InsideLay = new LinearLayout[4];
        DayWeather = new TextView[4];
        DayOfWeekIndex = 0;
//        for(int i = 0; i < DayOfWeek.length; i++){
//            if(presentDay.equals(DayOfWeek[i])) break;
//            DayOfWeekIndex++;
//        }

        for(int i = 0; i < DayWeather_Lay.length; i++){
            DayWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
            for(int j = 0; j < DayWeather_InsideLay.length; j++){
                DayWeather_InsideLay[j] = new LinearLayout(mainActivity.getApplicationContext());
                DayWeather[j] = new TextView(mainActivity.getApplicationContext());
                if(j == 0){
                    DayWeather[j].setText(presentDay+"요일");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#ff0000"));
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
                    LPS1.setMargins(0,0,3,3);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER_VERTICAL);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#99ccff"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round);

                } else if(j == 1){
                    DayWeather[j].setText("image");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#00ff00"));
                    DayWeather[j].setTextSize(20);
                    DayWeather[j].setTextColor(Color.parseColor("#ffffff"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
//                    LPS.setMargins(50,0,50,0);
//                    LPS.height = 200;
                    DayWeather[j].setLayoutParams(LPS);
//                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                    LinearLayout.LayoutParams LPS1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                    LPS1.weight = 1;
                    LPS1.setMargins(0,0,3,3);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#808080"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round);

                } else if(j == 2){
                    DayWeather[j].setText("날씨");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#964b00"));
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
                    LPS1.setMargins(0,0,3,3);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.CENTER);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#d09aff"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round);

                } else if(j == 3){
                    DayWeather[j].setText(Weather+"℃");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#0000ff"));
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
                    LPS1.setMargins(0,0,0,3);
                    DayWeather_InsideLay[j].setLayoutParams(LPS1);
                    DayWeather_InsideLay[j].setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    DayWeather_InsideLay[j].addView(DayWeather[j]);
//                    DayWeather_InsideLay[j].setBackgroundColor(Color.parseColor("#ff7f00"));
                    DayWeather_InsideLay[j].setBackgroundResource(R.drawable.round);

                }
                LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
                LPS.width = LinearLayout.LayoutParams.MATCH_PARENT;
                LPS.height = 200;
                DayWeather_Lay[i].setWeightSum(5);
                DayWeather_Lay[i].setLayoutParams(LPS);
                DayWeather_Lay[i].addView(DayWeather_InsideLay[j]);
                DayWeather_Lay[i].setGravity(Gravity.CENTER_VERTICAL);
                DayWeather_Lay[i].setBackgroundColor(Color.parseColor("#000000"));
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
        return DayWeather_Lay;

    }

    public void TimeWeatherReload(MainActivity mainActivity, LinearLayout scRoll, String presentWeather){
        scRoll.removeAllViews();
        TimeWeatherWidgetInit(mainActivity, presentWeather);
        for(LinearLayout L : TimeWeather_Lay)
            scRoll.addView(L);
    }

    private LinearLayout[] TimeWeatherWidgetInit(MainActivity mainActivity, String presentWeather){
        presentTime = new SimpleDateFormat("HH").format(new Date());
        TimeWeather = new TextView[9];
        TimeWeather_Lay = new LinearLayout[9];
        int tmp1 = 0;
        int tmp2 = 0;
        for(int i = 0; i < TimeWeather.length; i++){
            final Integer crt = Integer.parseInt(presentTime);
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

            TimeWeather[i] = new TextView(mainActivity.getApplicationContext());
            TimeWeather[i].setText(aMpM+(tmp2)+"시"+mainActivity.getString(R.string.lineSpace)+presentWeather+"℃");
            TimeWeather[i].setTextColor(Color.parseColor("#ffffff"));
            TimeWeather[i].setGravity(Gravity.CENTER|Gravity.TOP);
//            TimeWeather[i].setBackgroundResource(R.drawable.noimage|R.drawable.round_onlyborder);
            TimeWeather[i].setBackgroundColor(Color.parseColor("#ff0000"));
            TimeWeather[i].setPadding(5,5,5,5);

            TimeWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
            LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);        //높이, 너비
            LPS.setMargins(0,0,3,0);
            TimeWeather_Lay[i].setLayoutParams(LPS);
//            TimeWeather_Lay[i].setBackgroundResource(R.drawable.noimage);
            TimeWeather_Lay[i].addView(TimeWeather[i]);
        }
        return TimeWeather_Lay;
    }
}

/*
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class scrollViewinit {
    private static scrollViewinit Instance;
    String presentTime, presentDay;
    LinearLayout[] TimeWeather_Lay, DayWeather_Lay;
    TextView[] TimeWeather, DayWeather;
    String aMpM;

    private scrollViewinit(){}

    public static scrollViewinit getInstance(){
        if(Instance == null){
            synchronized (scrollViewinit.class){
                Instance = new scrollViewinit();
            }
        }
        return Instance;
    }

    public void addView(MainActivity mainActivity, LinearLayout scRoll, int index){
        if(index == 0){
            TimeWeatherWidgetInit(mainActivity, "");
            for(TextView t : TimeWeather)
                scRoll.addView(t);
        } else if(index == 1){
            DayWeatherWidgetInit(mainActivity, "");
            for(LinearLayout L : DayWeather_Lay)
                scRoll.addView(L);
            //Todo
        }
    }

    private LinearLayout[] DayWeatherWidgetInit(MainActivity mainActivity, String Weather){
        presentDay = new SimpleDateFormat("E").format(new Date());       //일주일 중 무슨 요일인지 또는 일주일 중 몇 번째 요일인지 또는 한달 중 몇일인지 포맷 변경 가능
        DayWeather_Lay = new LinearLayout[10];
        DayWeather = new TextView[4];
        final LinearLayout.LayoutParams LPS = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);        //높이, 너비
        LPS.weight = 1;
        LPS.height = 200;
        LPS.setMargins(30, 30, 30, 30);
        for(int i = 0; i < DayWeather_Lay.length; i++){
            DayWeather_Lay[i] = new LinearLayout(mainActivity.getApplicationContext());
            for(int j = 0; j < DayWeather.length; j++){
                DayWeather[j] = new TextView(mainActivity.getApplicationContext());
                if(j == 0){
                    DayWeather[j].setText(presentDay+"요일");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#ff0000"));
//                    DayWeather[j].setTextSize(30);
                    DayWeather[j].setGravity(Gravity.CENTER);
//                    DayWeather[j].setPadding(5,5,5,5);
                    DayWeather_Lay[i].addView(DayWeather[j], LPS);
                } else if(j == 1){
                    DayWeather[j].setText("image");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#00ff00"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    DayWeather_Lay[i].addView(DayWeather[j], LPS);
                } else if(j == 2){
                    DayWeather[j].setText("날씨");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#964b00"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    DayWeather_Lay[i].addView(DayWeather[j], LPS);
                } else if(j == 3){
                    DayWeather[j].setText(Weather+"℃");
                    DayWeather[j].setBackgroundColor(Color.parseColor("#0000ff"));
                    DayWeather[j].setGravity(Gravity.CENTER);
                    DayWeather_Lay[i].addView(DayWeather[j], LPS);
                }
            }


//            DayWeather[i] = new TextView(mainActivity.getApplicationContext());
//            DayWeather[i].setText(Weather+"℃");
//            DayWeather[i].setTextColor(Color.parseColor("#000000"));
//            DayWeather[i].setGravity(Gravity.CENTER);
//            DayWeather[i].setBackgroundResource(R.drawable.noimage);
//            DayWeather[i].setPadding(5,5,5,5);
        }
        DayWeather_Lay[0].setBackgroundColor(Color.parseColor("#ff5050"));
        DayWeather_Lay[1].setBackgroundColor(Color.parseColor("#f0f000"));
        DayWeather_Lay[2].setBackgroundColor(Color.parseColor("#f0505f"));
        DayWeather_Lay[3].setBackgroundColor(Color.parseColor("#0ff000"));
        DayWeather_Lay[4].setBackgroundColor(Color.parseColor("#0f4f10"));
        DayWeather_Lay[5].setBackgroundColor(Color.parseColor("#1f3525"));
        DayWeather_Lay[6].setBackgroundColor(Color.parseColor("#31f455"));
        DayWeather_Lay[7].setBackgroundColor(Color.parseColor("#ffffff"));
        DayWeather_Lay[8].setBackgroundColor(Color.parseColor("#f0f00f"));
        DayWeather_Lay[9].setBackgroundColor(Color.parseColor("#f0467f"));
        return DayWeather_Lay;
    }

    public void TimeWeatherReload(MainActivity mainActivity, LinearLayout scRoll, String presentWeather){
        scRoll.removeAllViews();
        TimeWeatherWidgetInit(mainActivity, presentWeather);
        for(TextView t : TimeWeather)
            scRoll.addView(t);
    }

    private TextView[] TimeWeatherWidgetInit(MainActivity mainActivity, String presentWeather){
        presentTime = new SimpleDateFormat("HH").format(new Date());
        TimeWeather = new TextView[9];
        int tmp1 = 0;
        int tmp2 = 0;
        for(int i = 0; i < TimeWeather.length; i++){
            final Integer crt = Integer.parseInt(presentTime);
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

            TimeWeather[i] = new TextView(mainActivity.getApplicationContext());
            TimeWeather[i].setText(aMpM+(tmp2)+"시"+mainActivity.getString(R.string.lineSpace)+presentWeather+"℃");
            TimeWeather[i].setTextColor(Color.parseColor("#000000"));
            TimeWeather[i].setGravity(Gravity.CENTER|Gravity.TOP);
            TimeWeather[i].setBackgroundResource(R.drawable.noimage);
            TimeWeather[i].setPadding(5,5,5,5);
        }
        return TimeWeather;
    }
}*/