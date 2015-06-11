package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;

public class DeviceHelper {

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

    public static String manufacturer() {
        return Build.MANUFACTURER;
    }

    public static String model() {
        return Build.MODEL;
    }

    public static String fingerprint() {
        return Build.FINGERPRINT;
    }

}
