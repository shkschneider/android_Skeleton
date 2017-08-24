package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.PowerManager;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Display;
import android.view.Surface;
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

    public static final int ROTATION_0 = Surface.ROTATION_0;
    public static final int ROTATION_90 = Surface.ROTATION_90;
    public static final int ROTATION_180 = Surface.ROTATION_180;
    public static final int ROTATION_270 = Surface.ROTATION_270;

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static void wakeLock(@NonNull final Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    public static void wakeUnlock(@NonNull final Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    public static void brightness(@NonNull final Window window, @FloatRange(from=BRIGHTNESS_RESET, to=BRIGHTNESS_MAX) final float brightness) {
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = ((brightness < BRIGHTNESS_MIN) ? BRIGHTNESS_RESET : brightness);
        window.setAttributes(layoutParams);
    }

    public static float density() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int dpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public static int height() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int width() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

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

    public static int dpFromPixels(@FloatRange(from=0.0) final float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pixelsFromDp(@FloatRange(from=0.0) final float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int pixelsFromSp(@FloatRange(from=0.0) final float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

}
