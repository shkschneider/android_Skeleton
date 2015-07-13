package me.shkschneider.skeleton.helper;

import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

public class IdHelper {

    protected IdHelper() {
        // Empty
    }

    // <https://code.google.com/p/android/issues/detail?id=10603>
    private static final String EMULATOR = "9774d56d682e549c";

    @Nullable
    public static String androidId() {
        final String androidId = Settings.Secure.getString(ApplicationHelper.context().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringHelper.nullOrEmpty(androidId)) {
            LogHelper.w("AndroidId was NULL");
            return null;
        }
        if (androidId.equals(EMULATOR)) {
            LogHelper.w("EMULATOR");
        }

        return StringHelper.lower(androidId);
    }

    @Deprecated
    @Nullable
    public static String imei() {
        final TelephonyManager telephonyManager = SystemServices.telephonyManager();
        if (telephonyManager == null) {
            LogHelper.w("TelephonyManager was NULL");
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    @Nullable
    public static String uuid() {
        final String deviceId = androidId();
        if (StringHelper.nullOrEmpty(deviceId)) {
            LogHelper.w("DeviceId was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
