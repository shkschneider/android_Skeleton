package me.shkschneider.skeleton.helper;

import android.content.res.Configuration;
import android.os.Build;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    @Deprecated // avoid
    public static boolean tablet() {
        final int screenLayout = ApplicationHelper.resources().getConfiguration().screenLayout;
        return ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    // <https://github.com/framgia/android-emulator-detector>
    @Deprecated // avoid
    public static boolean emulator() {
        boolean emulator = Build.FINGERPRINT.startsWith("generic")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HARDWARE.equals("goldfish")
                || Build.HARDWARE.equals("vbox86")
                || Build.PRODUCT.equals("sdk")
                || Build.PRODUCT.equals("google_sdk")
                || Build.PRODUCT.equals("sdk_x86")
                || Build.PRODUCT.equals("vbox86p")
                || Build.BOARD.toLowerCase().contains("nox")
                || Build.BOOTLOADER.toLowerCase().contains("nox")
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.SERIAL.toLowerCase().contains("nox");
        if (emulator) return true;
        emulator = Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");
        if (emulator) return true;
        emulator = Build.PRODUCT.equals("google_sdk");
        return emulator;

    }

    public static String architecture() {
        return SystemProperties.property(SystemProperties.SYSTEM_PROPERTY_OS_ARCH);
    }

    // End-user-friendly
    public static String model() {
        return Build.MODEL;
    }

    // End-user-friendly
    public static String brand() {
        return Build.BRAND;
    }

    public static String manufacturer() {
        return Build.MANUFACTURER;
    }

    public static String codename() {
        return Build.DEVICE;
    }

    public static String id() {
        return Build.ID;
    }

    @Deprecated // avoid
    public static boolean is64bits() {
        return architecture().contains("64");
    }

}
