package me.shkschneider.skeleton.helper;

import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    // <http://stackoverflow.com/a/15133818>
    @Deprecated // Avoid
    public static double screenSize() {
        final WindowManager windowManager = SystemServices.windowManager();
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        final int h = displayMetrics.heightPixels;
        final int w = displayMetrics.widthPixels;
        final double diagonalPixels = Math.sqrt(Math.pow(h, 2) + Math.pow(w, 2));
        return diagonalPixels / displayMetrics.densityDpi;
    }

    @SuppressWarnings("deprecation")
    @Deprecated // Avoid
    public static boolean tablet() {
        final double screenSize = screenSize();
        LogHelper.verbose("screenSize: " + screenSize + "\"");
        return (screenSize >= 7.0);
    }

    // <https://github.com/framgia/android-emulator-detector>
    @Deprecated // Avoid
    public static boolean emulator() {
        boolean emulator = Build.FINGERPRINT.startsWith("generic")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase(LocaleHelper.locale()).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HARDWARE.equals("goldfish")
                || Build.HARDWARE.equals("vbox86")
                || Build.PRODUCT.equals("sdk")
                || Build.PRODUCT.equals("google_sdk")
                || Build.PRODUCT.equals("sdk_x86")
                || Build.PRODUCT.equals("vbox86p")
                || Build.BOARD.toLowerCase(LocaleHelper.locale()).contains("nox")
                || Build.BOOTLOADER.toLowerCase(LocaleHelper.locale()).contains("nox")
                || Build.HARDWARE.toLowerCase(LocaleHelper.locale()).contains("nox")
                || Build.PRODUCT.toLowerCase(LocaleHelper.locale()).contains("nox")
                || Build.SERIAL.toLowerCase(LocaleHelper.locale()).contains("nox");
        if (emulator) return true;
        emulator = Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");
        if (emulator) return true;
        emulator = Build.PRODUCT.equals("google_sdk");
//        final TelephonyManager telephonyManager = SystemServices.telephonyManager();
//        if (telephonyManager != null) {
//            if (new ArrayList<String>() {
//                {
//                    add("000000000000000");
//                    add("e21833235b6eef10");
//                    add("012345678912345");
//                }
//            }.contains(telephonyManager.getDeviceId())) {
//                return true;
//            }
//        }
        return emulator;

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
        return SystemProperties.property(SystemProperties.SYSTEM_PROPERTY_OS_ARCH);
    }

    @Deprecated // Avoid
    public static boolean is64bits() {
        return architecture().contains("64");
    }

}
