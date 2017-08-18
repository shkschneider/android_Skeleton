package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

// <https://developers.google.com/instance-id/>
@SuppressLint("HardwareIds")
public class IdHelper {

    protected IdHelper() {
        // Empty
    }

    @Nullable
    public static String androidId() {
        final String androidId = Settings.Secure.getString(ContextHelper.applicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }
        return StringHelper.lower(androidId);
    }

    @Deprecated // Discouraged
    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String imei() {
        final TelephonyManager telephonyManager = SystemServices.telephonyManager();
        if (telephonyManager == null) {
            LogHelper.warning("TelephonyManager was NULL");
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    @Deprecated // Discouraged
    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String sim() {
        final TelephonyManager telephonyManager = SystemServices.telephonyManager();
        if (telephonyManager == null) {
            LogHelper.warning("TelephonyManager was NULL");
            return null;
        }
        return telephonyManager.getSimSerialNumber();
    }

    @Nullable
    public static String uuid() {
        final String deviceId = androidId();
        if (TextUtils.isEmpty(deviceId)) {
            LogHelper.warning("DeviceId was NULL");
            return null;
        }
        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
