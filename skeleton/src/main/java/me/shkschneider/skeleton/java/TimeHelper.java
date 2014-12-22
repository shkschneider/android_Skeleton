package me.shkschneider.skeleton.java;

import android.text.format.DateUtils;

import me.shkschneider.skeleton.helpers.LogHelper;

import java.util.Date;

public class TimeHelper {

    public static long timestamp() {
        return (System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS);
    }

    public static long millitimestamp() {
        return System.currentTimeMillis();
    }

    public static String relative(final long time) {
        if (time < 0) {
            LogHelper.warning("Time was invalid");
            return null;
        }

        return DateUtils.getRelativeTimeSpanString(time, new Date().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
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

