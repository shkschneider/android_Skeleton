package me.shkschneider.skeleton.helper;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {

    protected DateTimeHelper() {
        // Empty
    }

    public static int day() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int month() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int year() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String format(final Calendar calendar, final String format) {
        return new SimpleDateFormat(format, Locale.US).format(calendar.getTime());
    }

    public static long timestamp() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static long milliTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private static int gmtOffset() {
        return Math.round(TimeZone.getDefault().getOffset(milliTimestamp()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static String relative(final long time) {
        return relative(time, timestamp());
    }

    public static String relative(final long from, final long to) {
        if (from < 0) {
            LogHelper.w("TimeFrom was invalid");
            return null;
        }
        if (to < 0) {
            LogHelper.w("TimeTo was invalid");
            return null;
        }

        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

}
