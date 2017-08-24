package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    // <https://github.com/eyeem/deviceinfo>
    @SuppressLint("NewApi")
    public static float screenSize() {
        final WindowManager windowManager = SystemServices.windowManager();
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        Point point = new Point(1, 1);
        final Display display = windowManager.getDefaultDisplay();
        if (AndroidHelper.api() >= AndroidHelper.API_17) {
            display.getRealSize(point);
        }
        else {
            display.getSize(point);
        }
        float w = point.x / displayMetrics.xdpi;
        float h = point.y / displayMetrics.ydpi;
        final float screenSize = (float) Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
        LogHelper.verbose("screenSize: " + screenSize + "\"");
        return screenSize;
    }

    public static boolean tablet() {
        final double screenSize = screenSize();
        return (screenSize >= 7.0);
    }

    // <https://stackoverflow.com/a/13815880>
    @Deprecated // Discouraged
    public static boolean emulator() {
        // Build.FINGERPRINT: Do not attempt to parse this value.
        return Build.DEVICE.startsWith("generic") ||
                (is64bits() ? Build.HARDWARE.equalsIgnoreCase("ranchu") : Build.HARDWARE.equalsIgnoreCase("goldfish"));
    }

    public static String brand() {
        return Build.BRAND;
    }

    public static String manufacturer() {
        return Build.MANUFACTURER;
    }

    public static String model() {
        return Build.MODEL;
    }

    public static String codename() {
        return Build.DEVICE;
    }

    public static String architecture() {
        return SystemProperties.get(SystemProperties.OS_ARCH);
    }

    public static boolean is64bits() {
        return (AndroidHelper.api() >= AndroidHelper.ANDROID_5
                && Build.SUPPORTED_64_BIT_ABIS.length > 0);
    }

    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String serial() {
        if (AndroidHelper.api() >= AndroidHelper.API_26) {
            return serial26();
        }
        return serial9();
    }

    @TargetApi(AndroidHelper.API_26)
    private static String serial26() {
        return Build.getSerial();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("HardwareIds")
    @TargetApi(AndroidHelper.API_9)
    private static String serial9() {
        return Build.SERIAL;
    }

}
