package com.wonders.xlab.common.utils;

import android.support.annotation.IntRange;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_FORMAT_FULL = "yyyy-MM-dd HH:mm";
    private static Calendar mCalendar = Calendar.getInstance();

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

    public static String formatToTime(long date) {
        return DateUtil.format(new Date(date), DEFAULT_FORMAT_FULL);
    }

    public static String format(Date date, String format, String suffix) {

        StringBuilder str = new StringBuilder();

        SimpleDateFormat ft = new SimpleDateFormat(format, Locale.CHINESE);

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

    public static long parseToLong(String dateString, String format) {

        Date date = parse(dateString, format);
        if (date == null) {
            return 0;
        } else {
            return date.getTime();
        }
    }

    public static boolean isBiggerThenToday(long oldTime) {
        mCalendar.setTimeInMillis(oldTime);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return mCalendar.compareTo(today) == 1;
    }

    public static boolean isNewBiggerOrEvenThenOld(long newTime, long oldTime) {
        Calendar newC = Calendar.getInstance();
        newC.setTimeInMillis(newTime);
        newC.set(Calendar.HOUR_OF_DAY, 0);
        newC.set(Calendar.MINUTE, 0);
        newC.set(Calendar.SECOND, 0);

        Calendar oldC = Calendar.getInstance();
        oldC.setTimeInMillis(oldTime);
        oldC.set(Calendar.HOUR_OF_DAY, 0);
        oldC.set(Calendar.MINUTE, 0);
        oldC.set(Calendar.SECOND, 0);

        return newC.compareTo(oldC) >= 0;
    }

    public static boolean isTheSameDay(long oldDate, long currentDate) {
        String old = format(oldDate, "yyyy-MM-dd");
        String current = format(currentDate, "yyyy-MM-dd");

        return old.equals(current);
    }

    public static boolean isTheSameYear(long oldDate, long currentDate) {
        String old = format(oldDate, "yyyy");
        String current = format(currentDate, "yyyy");

        return old.equals(current);
    }

    /**
     * @param day Calendar.MONDAY...
     * @return
     */
    public static long getTimeOfWeek(@IntRange(from = 1, to = 7) int day) {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != day) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * time所在周，周一的日期
     *
     * @param time
     * @return
     */
    public static long calculateFirstDayOfWeek(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        while (mCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            mCalendar.add(Calendar.DATE, -1);
        }
        return mCalendar.getTimeInMillis();
    }

    /**
     * time所在周，周日的日期
     *
     * @param time
     * @return
     */
    public static long calculateLastDayOfWeek(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        while (mCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            mCalendar.add(Calendar.DATE, 1);
        }
        return mCalendar.getTimeInMillis();
    }

    /**
     * time所在月的第一天日期
     *
     * @param time
     * @return
     */
    public static long calculateFirstDayOfMonth(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return mCalendar.getTimeInMillis();
    }

    /**
     * time所在月的最后一天日期
     *
     * @param time
     * @return
     */
    public static long calculateLastDayOfMonth(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);

        mCalendar.add(Calendar.MONTH, 1);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mCalendar.add(Calendar.DAY_OF_MONTH, -1);

        return mCalendar.getTimeInMillis();
    }

    /**
     * time所在月的第一天日期
     *
     * @param time
     * @return
     */
    public static long calculateFirstDayOfYear(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return mCalendar.getTimeInMillis();
    }

    /**
     * time所在月的最后一天日期
     *
     * @param time
     * @return
     */
    public static long calculateLastDayOfYear(long time) {
        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCalendar.setTimeInMillis(time);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);

        mCalendar.add(Calendar.YEAR, 1);
        mCalendar.set(Calendar.MONTH, Calendar.JANUARY);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mCalendar.add(Calendar.DAY_OF_MONTH, -1);

        return mCalendar.getTimeInMillis();
    }
}
