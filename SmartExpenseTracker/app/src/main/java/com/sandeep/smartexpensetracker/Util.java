package com.sandeep.smartexpensetracker;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Util {
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);  // Current day
        int month = calendar.get(Calendar.MONTH) + 1;  // Current month (1-based, so adding 1)
        int year = calendar.get(Calendar.YEAR);
        return day+"-"+month+"-"+year;
    }

    public static HashMap<String, Double> calculatePercentages(double[] total, HashMap<String, Double> map) {
        HashMap<String, Double> per = new HashMap<>();
        for (String i : map.keySet()) {
            per.put(i, ((map.get(i)/total[0])*100));
            Log.d("Sandeep", i+"---"+((map.get(i)/total[0])*100));
        }
        return per;
    }

    static long convertDateToTimestamp(int day, int month, int year) {
        try {
            String formattedDate = String.format("%02d%02d%04d", day, month, year);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date date = sdf.parse(formattedDate);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}





