package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

    // <https://code.google.com/p/android/issues/detail?id=10603>
    private static final String EMULATOR = "9774d56d682e549c";

    @Deprecated // Avoid
    @Nullable
    public static String androidId(@NonNull final Context context) {
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }
        if (androidId.equals(EMULATOR)) {
            LogHelper.warning("EMULATOR");
        }
        return StringHelper.lower(androidId);
    }

    @Deprecated // Avoid
    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String imei(@NonNull final Context context) {
        final TelephonyManager telephonyManager = SystemServices.telephonyManager(context);
        if (telephonyManager == null) {
            LogHelper.warning("TelephonyManager was NULL");
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    @Deprecated // Avoid
    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String sim(@NonNull final Context context) {
        final TelephonyManager telephonyManager = SystemServices.telephonyManager(context);
        if (telephonyManager == null) {
            LogHelper.warning("TelephonyManager was NULL");
            return null;
        }
        return telephonyManager.getSimSerialNumber();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public static String uuid(@NonNull final Context context) {
        final String deviceId = androidId(context);
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
