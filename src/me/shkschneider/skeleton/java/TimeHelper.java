package me.shkschneider.skeleton.java;

import android.text.format.DateUtils;

import java.util.Date;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class TimeHelper {

    public static long timestamp() {
        return (System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS);
    }

    public static long mstimestamp() {
        return System.currentTimeMillis();
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

    public static String relative(final long time) {
        return relative(time, new Date().getTime());
    }

}

