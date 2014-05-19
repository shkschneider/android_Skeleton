package me.shkschneider.skeleton.helpers;

import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class DeviceHelper {

    public static Boolean tablet() {
        if (AndroidHelper.api() >= Build.VERSION_CODES.HONEYCOMB) {
            final Configuration configuration = ApplicationHelper.resources().getConfiguration();
            if (configuration == null) {
                LogHelper.warning("Configuration was NULL");
                return null;
            }

            try {
                final Method method = configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                return (Boolean) method.invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
                return null;
            }
        }
        else {
            LogHelper.debug("Api was < HONEYCOMB");
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

    public static TelephonyManager sim() {
        return (TelephonyManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_TELEPHONY);
    }

}
