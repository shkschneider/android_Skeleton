package me.shkschneider.skeleton.helper;

import android.os.SystemClock;

// <http://developer.android.com/reference/android/content/Context.html>
public class SystemHelper {

    protected SystemHelper() {
        // Empty
    }

    public static String uname() {
        return String.format(LocaleHelper.locale(),
                "%s %s %s",
                System.getProperty(SystemProperties.SYSTEM_PROPERTY_OS_NAME),
                System.getProperty(SystemProperties.SYSTEM_PROPERTY_OS_VERSION),
                System.getProperty(SystemProperties.SYSTEM_PROPERTY_OS_ARCH));
    }

    public static long sinceBoot() {
        return SystemClock.elapsedRealtime();
    }

    @Deprecated // Discouraged
    public static void sleep(final long milliseconds) {
        SystemClock.sleep(milliseconds);
    }

}
