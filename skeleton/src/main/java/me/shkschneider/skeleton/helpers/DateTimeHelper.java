package me.shkschneider.skeleton.helpers;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeHelper {

    public static Calendar calendar(final int year, final int month, final int day, final int hour, final int minutes, final int seconds) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Calendar calendar(final int year, final int month, final int day) {
        return calendar(day, month, year, 0, 0, 0);
    }

    public static int year() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int month() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int day() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static String format(final String format, final Calendar calendar) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static long timestamp() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static long millitimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private static int gmtOffset() {
        return Math.round(TimeZone.getDefault().getOffset(millitimestamp()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static String relative(final long time) {
        if (time < 0) {
            LogHelper.warning("Time was invalid");
            return null;
        }

        return DateUtils.getRelativeTimeSpanString(time, timestamp(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

    public static String relative(final long from, final long to) {
        if (from < 0) {
            LogHelper.warning("TimeFrom was invalid");
            return null;
        }
        if (to < 0) {
            LogHelper.warning("TimeTo was invalid");
            return null;
        }

        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

}
