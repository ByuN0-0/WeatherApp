package com.cookandroid.location_show;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Opacity {
    public static String sunrise;
    public static String sunset;
    public static String sunsetInt;
    public static String sunsetInt1;
    public static String sunsetSum;
    Date curDate;
    String currentTime;
    SimpleDateFormat dFormat;
    @SuppressLint("SimpleDateFormat")
    Opacity(){
        sunrise = "07시 00분";
        sunset = "20시 30분";
        curDate = new Date();
        dFormat = new SimpleDateFormat("HH시 mm분");
        currentTime = dFormat.format(curDate);
    }
    @SuppressLint("SimpleDateFormat")
    Opacity(String _sunrise, String _sunset){
        sunrise = _sunrise;
        sunset = _sunset;
        sunsetInt = sunset.substring(0,2);
        sunsetInt1 = sunset.substring(4,6);
        sunsetSum = sunsetInt+sunsetInt1;
        curDate = new Date();
        dFormat = new SimpleDateFormat("HH시 mm분");
        currentTime = dFormat.format(curDate);
    }
    public int[] setBackgroundImg() throws ParseException {
        //Object[] values = new Object[2];
        int[] values = new int[12];

        Date sunriseDate = dFormat.parse(sunrise);
        Date sunsetDate = dFormat.parse(sunset);
        Date currentDate = dFormat.parse(currentTime);
        Date noonDate = dFormat.parse("12시 00분");
        Date nightDate = dFormat.parse("23시 59분");
        System.out.println("sunriseDate : "+ sunriseDate);
        System.out.println("sunsetDate : "+ sunsetDate);

        Calendar time0 = Calendar.getInstance();
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        Calendar time3 = Calendar.getInstance();
        Calendar time4 = Calendar.getInstance();
        Calendar time5 = Calendar.getInstance();
        Calendar time6 = Calendar.getInstance();
        Calendar time7 = Calendar.getInstance();
        Calendar time8 = Calendar.getInstance();
        Calendar time9 = Calendar.getInstance();


        time0.setTime(sunriseDate); //일출 30분 전
        time1.setTime(sunriseDate); //일출
        time2.setTime(sunriseDate); //일출 30분 후
        time3.setTime(sunriseDate); //일출 1시간 후
        time4.setTime(noonDate); // 12:00
        time5.setTime(sunsetDate); //일출 1시간 전
        time6.setTime(sunsetDate);  //일몰 30분 전
        time7.setTime(sunsetDate);  //일몰
        time8.setTime(sunsetDate);  //일몰 30분 후
        time9.setTime(nightDate); // 24:00

        time0.add(Calendar.MINUTE,-30);
        time2.add(Calendar.MINUTE,30);
        time3.add(Calendar.HOUR_OF_DAY, 1);
        time5.add(Calendar.HOUR_OF_DAY, -1);
        time6.add(Calendar.MINUTE, -30);
        time8.add(Calendar.MINUTE, 30);

        Date date0 = time0.getTime();
        Date date1 = time1.getTime();
        Date date2 = time2.getTime();
        Date date3 = time3.getTime();
        Date date4 = time4.getTime();
        Date date5 = time5.getTime();
        Date date6 = time6.getTime();
        Date date7 = time7.getTime();
        Date date8 = time8.getTime();
        Date date9 = time9.getTime();

        System.out.println("------------date---------\n");
        System.out.println("sunriseDate = "+sunriseDate);
        System.out.println("sunsetDate = "+sunsetDate);
        System.out.println("currentDate = "+currentDate);
        System.out.println("Date0 = "+date0);
        System.out.println("Date1 = "+date1);
        System.out.println("Date2 = "+date2);
        System.out.println("Date3 = "+date3);
        System.out.println("Date4 = "+date4);
        System.out.println("Date5 = "+date5);
        System.out.println("Date6 = "+date6);
        System.out.println("Date7 = "+date7);
        System.out.println("------------date---------\n");

        values[0]=calculateTime("00시 00분",dFormat.format(date0));
        values[1]=calculateTime(dFormat.format(date0),dFormat.format(date1));
        values[2]=calculateTime(dFormat.format(date1),dFormat.format(date2));
        values[3]=calculateTime(dFormat.format(date2),dFormat.format(date3));
        values[4]=calculateTime(dFormat.format(date3),dFormat.format(date4));
        values[5]=calculateTime(dFormat.format(date4),dFormat.format(date5));
        values[6]=calculateTime(dFormat.format(date5),dFormat.format(date6));
        values[7]=calculateTime(dFormat.format(date6),dFormat.format(date7));
        values[8]=calculateTime(dFormat.format(date7),dFormat.format(date8));
        values[9]=calculateTime(dFormat.format(date8),dFormat.format(date9));

        System.out.println(values[0]+" "+values[1]+" "+values[2]+" "+values[3]+" "+values[4]+" "+values[5]+" "+values[6]+" "+values[7]+" "+values[8]+" "+values[9]);
        if(date0.after(currentDate)){
            values[11]=8;
            values[10]=calculateTime(currentTime, dFormat.format(date0));
            System.out.println(dFormat.format(date0) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date1.after(currentDate) || date0.equals(currentDate)){
            values[11]=0;
            values[10]=calculateTime(currentTime, dFormat.format(date1));
            System.out.println(dFormat.format(date1) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date2.after(currentDate) || date1.equals(currentDate)){
            values[11]=1;
            values[10]=calculateTime(currentTime, dFormat.format(date2));
            System.out.println(dFormat.format(date2) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date3.after(currentDate) || date2.equals(currentDate)){
            values[11]=2;
            values[10]=calculateTime(currentTime, dFormat.format(date3));
            System.out.println(dFormat.format(date3) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date4.after(currentDate) || date3.equals(currentDate)){
            values[11]=3;
            values[10]=calculateTime(currentTime, dFormat.format(date4));
            System.out.println(dFormat.format(date4) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date5.after(currentDate) || date4.equals(currentDate)){
            values[11]=4;
            values[10]=calculateTime(currentTime, dFormat.format(date5));
            System.out.println(dFormat.format(date5) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date6.after(currentDate) || date5.equals(currentDate)){
            values[11]=5;
            values[10]=calculateTime(currentTime, dFormat.format(date6));
            System.out.println(dFormat.format(date6) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date7.after(currentDate) || date6.equals(currentDate)){
            values[11]=6;
            values[10]=calculateTime(currentTime, dFormat.format(date7));
            System.out.println(dFormat.format(date7) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else if(date8.after(currentDate) || date7.equals(currentDate)){
            values[11]=7;
            values[10]=calculateTime(currentTime, dFormat.format(date8));
            System.out.println(dFormat.format(date8) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }else{
            values[11]=8;
            values[10]=calculateTime(currentTime, dFormat.format(date9));
            System.out.println(dFormat.format(date9) + currentTime);
            System.out.println("bgindex : "+values[11]);
            return values;
        }
    }
    public static int calculateTime(String before, String after) {
        int afterMinutes = convertToMinutes(after);
        int beforeMinutes = convertToMinutes(before);

        int timeDifference = afterMinutes - beforeMinutes;
        if (timeDifference==0){ timeDifference = 60;}
        return timeDifference;
    }
    private static int convertToMinutes(String time) {
        String[] parts = time.split("시 ");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1].replace("분", ""));

        return hours * 60 + minutes;
    }
}