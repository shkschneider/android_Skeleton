package me.shkschneider.skeleton.helpers;

import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.security.HashHelper;

@SuppressWarnings("unused")
public final class DeviceHelper {

    public static Boolean tablet() {
        if (AndroidHelper.api() >= Build.VERSION_CODES.HONEYCOMB) {
            final Configuration configuration = SkeletonApplication.CONTEXT.getResources().getConfiguration();
            if (configuration == null) {
                LogHelper.warning("Configuration was NULL");
                return null;
            }

            try {
                final Method method = configuration.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                return (Boolean) method.invoke(configuration, Configuration.SCREENLAYOUT_SIZE_XLARGE);
            }
            catch (final NoSuchMethodException e) {
                LogHelper.error("NoSuchMethodException: " + e.getMessage());
                return null;
            }
            catch (final IllegalAccessException e) {
                LogHelper.error("IllegalAccessException: " + e.getMessage());
                return null;
            }
            catch (final InvocationTargetException e) {
                LogHelper.error("InvocationTargetException: " + e.getMessage());
                return null;
            }
        }
        else {
            LogHelper.debug("Api was < HONEYCOMB");
            return false;
        }
    }

    public static String id() {
        final String androidId = AndroidHelper.id();
        if (StringHelper.nullOrEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }

        return HashHelper.md5(androidId).toLowerCase();
    }

    public static String uuid() {
        final String deviceId = DeviceHelper.id();
        if (StringHelper.nullOrEmpty(deviceId)) {
            LogHelper.warning("DeviceId was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString().replace("-", "");
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString().replace("-", "");
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
