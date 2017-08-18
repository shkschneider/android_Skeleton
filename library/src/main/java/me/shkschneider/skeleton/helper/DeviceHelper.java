package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

// <http://developer.android.com/reference/android/os/Build.html>
public class DeviceHelper {

    protected DeviceHelper() {
        // Empty
    }

    // <https://github.com/eyeem/deviceinfo>
    @SuppressWarnings("deprecation")
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
        else if (AndroidHelper.api() >= AndroidHelper.API_13) {
            display.getSize(point);
        }
        else {
            point = new Point(display.getWidth(), display.getHeight());
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

    // <https://github.com/framgia/android-emulator-detector>
    @Deprecated // Discouraged
    public static boolean emulator() {
        @SuppressLint("HardwareIds")
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
                || Build.PRODUCT.toLowerCase(LocaleHelper.locale()).contains("nox");
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
        return SystemProperties.get(SystemProperties.OS_ARCH);
    }

    public static boolean is64bits() {
        if (AndroidHelper.api() < AndroidHelper.ANDROID_5) {
            return false;
        }
        return Build.SUPPORTED_64_BIT_ABIS.length > 0;
    }

    // TODO
//    @TargetApi(AndroidHelper.API_26)
//    public static String getSerial() {
//        return Build.getSerial();
//    }

}
