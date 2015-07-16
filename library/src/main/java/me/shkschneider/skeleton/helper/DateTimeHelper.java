package me.shkschneider.skeleton.helper;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {

    // <http://developer.android.com/intl/ru/reference/java/text/SimpleDateFormat.html>
    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

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

    public static String format(@NonNull final Calendar calendar, @NonNull final String format) {
        return new SimpleDateFormat(format, Locale.US).format(calendar.getTime());
    }

    public static long timestamp() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static long milliTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static int gmtOffset() {
        return Math.round(TimeZone.getDefault().getOffset(milliTimestamp()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static String relative(@IntRange(from=0) final long time) {
        return relative(time, timestamp());
    }

    @Nullable
    public static String relative(@IntRange(from=0) final long from, @IntRange(from=0) final long to) {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

}
