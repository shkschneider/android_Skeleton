package me.shkschneider.skeleton.java;

import java.util.concurrent.TimeUnit;

public class TimeUnitsHelper {

    public static long seconds(final int amount, final TimeUnit timeUnit) {
        return TimeUnit.SECONDS.convert(amount, timeUnit);
    }

    public static long milliseconds(final int amount, final TimeUnit timeUnit) {
        return TimeUnit.MILLISECONDS.convert(amount, timeUnit);
    }

}
