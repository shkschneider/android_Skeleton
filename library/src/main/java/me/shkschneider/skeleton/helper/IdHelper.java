package me.shkschneider.skeleton.helper;

import android.provider.Settings;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

public class IdHelper {

    protected IdHelper() {
        // Empty
    }

    public static String androidId() {
        final String androidId = Settings.Secure.getString(ApplicationHelper.context().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringHelper.nullOrEmpty(androidId)) {
            Log.w("AndroidId was NULL");
            return null;
        }

        return StringHelper.lower(androidId);
    }

    public static String uuid() {
        final String deviceId = androidId();
        if (StringHelper.nullOrEmpty(deviceId)) {
            Log.w("DeviceId was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
