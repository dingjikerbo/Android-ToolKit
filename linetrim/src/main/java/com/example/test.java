package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liwentian on 2017/12/29.
 */

public class test {

    private static final SimpleDateFormat UTC_SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

//    public static String utcToLocalTime(String utc) {
//        long timestamp = utcToLocalTimeStamp(utc);
//        return LOCAL_SDF.format(timestamp);
//    }

    public static long utcToLocalTimeStamp(String utc) {
        try {
            Date date = UTC_SDF.parse(utc);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        long n = utcToLocalTimeStamp("2017-12-29T02:45:01.789Z");
        System.out.println(n);
        System.out.println(UTC_SDF.format(n));
    }
}
