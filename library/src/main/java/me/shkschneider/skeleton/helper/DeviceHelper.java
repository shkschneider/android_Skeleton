package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;

import java.lang.reflect.Method;

public class DeviceHelper {

    @Deprecated
    public static boolean tablet() {
        final Configuration configuration = ApplicationHelper.resources().getConfiguration();
        if (configuration == null) {
            Log.w("Configuration was NULL");
            return false;
        }

        try {
            final Method method = configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class);
            return (Boolean) method.invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return false;
        }
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
