package com.wonders.xlab.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return format(date, format, "");
    }

    public static String format(int year, int month, int day, String format) {
        return DateUtil.format(DateUtil.parse(year + "/" + month + "/" + day, DateUtil.DEFAULT_FORMAT), format);
    }

    public static String format(long date, String format) {
        return DateUtil.format(new Date(date), format);
    }

    public static String format(Date date, String format, String suffix) {

        StringBuilder str = new StringBuilder();

        SimpleDateFormat ft = new SimpleDateFormat(format,Locale.CHINESE);

        str.append(ft.format(date));

        str.append(suffix);

        return str.toString();
    }

    public static Date parse(String dateString, String format) {

        if (TextUtils.isEmpty(dateString)) {
            return null;
        }
        SimpleDateFormat ft = new SimpleDateFormat(format, Locale.CHINESE);

        try {
            return ft.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
