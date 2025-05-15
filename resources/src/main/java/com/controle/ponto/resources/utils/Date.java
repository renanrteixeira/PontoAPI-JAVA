package com.controle.ponto.resources.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public static String formatHour(java.util.Date hora, boolean negative){
        String pattern = "HH:mm:ss";
        if (negative){
            pattern = "-HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(hora);
    }

    public static String formatDate(java.util.Date date, String pattern){
//        String pattern = "YYYY-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);
    }

    public static java.util.Date calendarGetTime(int hour, int minute) {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR, hour);
        c1.set(Calendar.MINUTE, minute);
        return c1.getTime();
    }

}
