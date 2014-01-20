package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import me.shkschneider.skeleton.SkeletonApplication;

@SuppressWarnings("unused")
public final class ScreenHelper {

    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    public static boolean wakeLock(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    public static boolean on() {
        return ((PowerManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_POWER_SERVICE)).isScreenOn();
    }

    public static int density() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().densityDpi;
    }

    public static int height() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().heightPixels;
    }

    public static int statusBarHeight(final Window window) {
        if (window == null) {
            LogHelper.warning("Window was NULL");
            return 0;
        }

        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int actionBarHeight(final Window window) {
        if (window == null) {
            LogHelper.warning("Window was NULL");
            return 0;
        }

        int top = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return top - statusBarHeight(window);
    }

    public static int width() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().widthPixels;
    }

    public static int orientation() {
        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getRotation();
    }

    public static int pixelsFromDp(final float dp) {
        if (dp <= 0F) {
            LogHelper.warning("Dp was too low");
            return 0;
        }

        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SkeletonApplication.CONTEXT.getResources().getDisplayMetrics()));
    }

}
