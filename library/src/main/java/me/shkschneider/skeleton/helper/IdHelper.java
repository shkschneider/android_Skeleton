package me.shkschneider.skeleton.helper;

import android.provider.Settings;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

public class IdHelper {

    protected IdHelper() {
        // Empty
    }

    // <https://code.google.com/p/android/issues/detail?id=10603>
    private static final String EMULATOR = "9774d56d682e549c";

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
