package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.os.PowerManager;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import me.shkschneider.skeleton.R;

public class ScreenHelper {

    protected ScreenHelper() {
        // Empty
    }

    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_TVDPI = 213; // MDPI * 1.33
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;

    public static final float BRIGHTNESS_RESET = -1.0F;
    public static final float BRIGHTNESS_MIN = 0.0F;
    public static final float BRIGHTNESS_MAX = 1.0F;

    public static final int ROTATION_0 = 0;
    public static final int ROTATION_90 = 1; // 90 counter-clockwise
    public static final int ROTATION_180 = 2; // upside-down
    public static final int ROTATION_240 = 3; // 90 clockwise

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static boolean wakeLock(@NonNull final Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static boolean wakeUnlock(@NonNull final Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    public static boolean on() {
        final PowerManager powerManager = SystemServices.powerManager();
        if (powerManager == null) {
            LogHelper.warning("PowerManager was NULL");
            return true;
        }
        if (AndroidHelper.api() >= AndroidHelper.API_20) {
            return on20(powerManager);
        }
        else {
            return on7(powerManager);
        }
    }

    @TargetApi(AndroidHelper.API_20)
    private static boolean on20(@NonNull final PowerManager powerManager) {
        return powerManager.isInteractive();
    }

    @SuppressWarnings("deprecation")
    private static boolean on7(@NonNull final PowerManager powerManager) {
        return powerManager.isScreenOn();
    }

    public static float brightness(@NonNull final Window window) {
        return window.getAttributes().screenBrightness;
    }

    public static boolean brightness(@NonNull final Window window, @FloatRange(from=BRIGHTNESS_RESET, to=BRIGHTNESS_MAX) final float brightness) {
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = ((brightness < BRIGHTNESS_MIN) ? BRIGHTNESS_RESET : brightness);
        window.setAttributes(layoutParams);
        return true;
    }

    public static float density() {
        return ApplicationHelper.resources().getDisplayMetrics().density;
    }

    public static int dpi() {
        return ApplicationHelper.resources().getDisplayMetrics().densityDpi;
    }

    public static int height() {
        return ApplicationHelper.resources().getDisplayMetrics().heightPixels;
    }

    public static int width() {
        return ApplicationHelper.resources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public static int statusBarHeight() {
        return (int) ApplicationHelper.resources().getDimension(R.dimen.statusBar);
    }

    public static int rotation() {
        final WindowManager windowManager = SystemServices.windowManager();
        if (windowManager == null) {
            LogHelper.warning("WindowManager was NULL");
            return 0;
        }
        final Display display = windowManager.getDefaultDisplay();
        if (display == null) {
            LogHelper.warning("Display was NULL");
            return 0;
        }

        return display.getRotation();
    }

    @Deprecated
    public static int orientation() {
        return rotation();
    }

    public static int pixelsFromDp(@FloatRange(from=0.0) final float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ApplicationHelper.resources().getDisplayMetrics()));
    }

    public static int pixelsFromSp(@FloatRange(from=0.0) final float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, ApplicationHelper.resources().getDisplayMetrics());
    }

}
