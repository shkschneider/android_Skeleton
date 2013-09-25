package me.shkschneider.skeleton;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.WindowManager;

@SuppressWarnings("unused")
public class Screens {

    // <http://developer.android.com/reference/android/util/DisplayMetrics.html>
    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    public static void wakeLock(final Activity activity) {
        if (activity != null) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static Boolean isOn(final Context context) {
        if (context != null) {
            return ((PowerManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_POWER_SERVICE)).isScreenOn();
        }
        else {
            Log.w("Context was NULL");
        }
        return false;
    }

    public static Integer density(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().densityDpi;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

    public static Integer height(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

    public static Integer width(final Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

    public static Integer orientation(final Context context) {
        return ((WindowManager) Systems.systemService(context, Systems.SYSTEM_SERVICE_WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    public static Integer pixelsFromDp(final Context context, final Float dp) {
        if (context != null) {
            if (dp > 0) {
                return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            }
            else {
                return 0;
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return 0;
    }

}
