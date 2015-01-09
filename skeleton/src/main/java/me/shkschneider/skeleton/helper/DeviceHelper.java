package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class DeviceHelper {

    public static Boolean tablet() {
        final Configuration configuration = ApplicationHelper.resources().getConfiguration();
        if (configuration == null) {
            LogHelper.warning("Configuration was NULL");
            return false;
        }

        try {
            final Method method = configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class);
            return (Boolean) method.invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
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

    @Deprecated
    public static TelephonyManager sim() {
        return SystemServices.telephonyManager();
    }

}
