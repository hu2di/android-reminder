package com.blogspot.huydungdinh.reminder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HUNGDH on 9/23/2015.
 */
public class MyDate {

    public static String getDate(Date date)
    {
        String result;
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(date);
        return result;
    }

    public static String getTime(Date date)
    {
        String result;
        String strDateFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(date);
        return result;
    }

    public static String getStandard(Date date) {
        String result;
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        result = sdf.format(date);
        return result;
    }
}
