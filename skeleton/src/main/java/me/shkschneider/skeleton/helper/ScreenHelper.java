package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import me.shkschneider.skeleton.SkeletonApplication;

public class ScreenHelper {

    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_TVDPI = 213; // MDPI * 1.33
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    public static boolean wakeLock(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    public static boolean on() {
        final PowerManager powerManager = (PowerManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_POWER);
        if (AndroidHelper.api() >= AndroidHelper.API_20) {
            return onNew(powerManager);
        }
        else {
            return onOld(powerManager);
        }
    }

    public static float brightness(@NonNull final Window window) {
        // from 0 (dark) to 1 (bright)
        return window.getAttributes().screenBrightness;
    }

    public static boolean brightness(@NonNull final Window window, final float brightness) {
        // -1 resets to default
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightness;
        window.setAttributes(layoutParams);
        return true;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    private static boolean onOld(@NonNull final PowerManager powerManager) {
        return powerManager.isScreenOn();
    }

    @TargetApi(AndroidHelper.API_20)
    private static boolean onNew(@NonNull final PowerManager powerManager) {
        return powerManager.isInteractive();
    }

    public static float density() {
        return ApplicationHelper.resources().getDisplayMetrics().density;
    }

    public static int densityDpi() {
        return ApplicationHelper.resources().getDisplayMetrics().densityDpi;
    }

    public static int height() {
        return ApplicationHelper.resources().getDisplayMetrics().heightPixels;
    }

    @Deprecated
    public static int statusBarHeight(@NonNull final Window window) {
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    @Deprecated
    public static int actionBarHeight(@NonNull final Window window) {
        int top = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return (top - statusBarHeight(window));
    }

    public static int width() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().widthPixels;
    }

    public static int orientation() {
        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW);
        final Display display = windowManager.getDefaultDisplay();
        if (display == null) {
            LogHelper.warning("Display was NULL");
            return 0;
        }

        return display.getRotation();
    }

    public static int pixelsFromDp(final float dp) {
        if (dp <= 0F) {
            LogHelper.warning("Dp was too low");
            return 0;
        }
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SkeletonApplication.CONTEXT.getResources().getDisplayMetrics()));
    }

}
