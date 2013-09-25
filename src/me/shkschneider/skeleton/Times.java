package me.shkschneider.skeleton;

import android.text.format.DateUtils;

import java.util.Date;

@SuppressWarnings("unused")
public class Times {

    // UNIX Timestamp (length: 1-11)

    public static Long timestamp() {
        return (java.lang.System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS);
    }

    // Relative elapsed time

    public static String relative(final Long time) {
        return DateUtils.getRelativeTimeSpanString(time, new Date().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

    public static String relative(final Long from, final Long to) {
        return DateUtils.getRelativeTimeSpanString(from, to, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

}

