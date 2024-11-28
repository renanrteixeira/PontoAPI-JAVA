package com.controle.ponto.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String formatHour(Date hora, boolean negative){
        String pattern = "HH:mm:ss";
        if (negative){
            pattern = "-HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(hora);
    }

    public static String formatDate(Date date){
        String pattern = "YYYY-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);
    }

    public static Date calendarGetTime(int hour, int minute) {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR, hour);
        c1.set(Calendar.MINUTE, minute);
        return c1.getTime();
    }
}
