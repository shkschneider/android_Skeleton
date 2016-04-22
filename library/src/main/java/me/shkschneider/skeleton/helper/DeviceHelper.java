package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    @Deprecated // avoid
    public static boolean tablet() {
        final int screenLayout = ApplicationHelper.resources().getConfiguration().screenLayout;
        return ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public static String architecture() {
        return SystemProperties.property(SystemProperties.SYSTEM_PROPERTY_OS_ARCH);
    }

    // End-user-friendly
    public static String model() {
        return Build.MODEL;
    }

    // End-user-friendly
    public static String brand() {
        return Build.BRAND;
    }

    public static String manufacturer() {
        return Build.MANUFACTURER;
    }

    public static String codename() {
        return Build.DEVICE;
    }

    public static String id() {
        return Build.ID;
    }

    @Deprecated // avoid
    public static boolean is64bits() {
        return architecture().contains("64");
    }

}
