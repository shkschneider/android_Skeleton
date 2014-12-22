package me.shkschneider.skeleton.helpers;

import android.os.SystemClock;

public class SystemHelper {

    public static String uname() {
        return String.format("%s %s %s", SystemProperties.osName(), SystemProperties.osVersion(), SystemProperties.osArch());
    }

    public static long sinceBoot() {
        return SystemClock.elapsedRealtime();
    }

    public static long sinceCurrentThreadBirth() {
        return SystemClock.currentThreadTimeMillis();
    }

    public static void safeSleep(final long milliseconds) {
        SystemClock.sleep(milliseconds);
    }

}
