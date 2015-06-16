package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    @Deprecated
    public static boolean tablet() {
        final int screenLayout = ApplicationHelper.resources().getConfiguration().screenLayout;
        // LARGE = 480x640dp "Nexus 7"
        // XLARGE = 720x960dp "Nexus 10"
        return ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public static String codename() {
        return Build.DEVICE;
    }

    // Customer-friendly
    public static String model() {
        return Build.MODEL;
    }

    public static String manufacturer() {
        return Build.MANUFACTURER;
    }

    // Customer-friendly
    public static String brand() {
        return Build.BRAND;
    }

    @Deprecated
    public static String id() {
        return Build.ID;
    }

}
