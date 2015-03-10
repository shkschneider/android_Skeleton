package me.shkschneider.skeleton.helper;

import android.provider.Settings;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

public class IdHelper {

    public static String androidId() {
        final String androidId = Settings.Secure.getString(ApplicationHelper.context().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringHelper.nullOrEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }

        return (androidId != null ? StringHelper.lower(androidId) : null);
    }

    public static String uuid() {
        final String deviceId = androidId();
        if (StringHelper.nullOrEmpty(deviceId)) {
            LogHelper.warning("DeviceId was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
