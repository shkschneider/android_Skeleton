package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

import me.shkschneider.skeleton.SkeletonApplication;

/**
 * Simple static class to handle android screens.
 *
 * @author Alan Schneider
 * @see android.util.DisplayMetrics
 * @since 1.0
 */
@SuppressWarnings("unused")
public class ScreenHelper {

    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_TVDPI = 213; // MDPI * 1.33
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    /**
     * @param activity to work with
     * @return worked (true) or not (false)
     * @since 1.0
     */
    public static boolean wakeLock(@NotNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    /**
     * @return is on (true) or not (false)
     * @since 1.0
     */
    public static boolean on() {
        final PowerManager powerManager = (PowerManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_POWER_SERVICE);
        if (AndroidHelper.api() >= AndroidHelper.API_20) {
            return onNew(powerManager);
        }
        else {
            return onOld(powerManager);
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    private static boolean onOld(final PowerManager powerManager) {
        return powerManager.isScreenOn();
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    private static boolean onNew(final PowerManager powerManager) {
        return powerManager.isInteractive();
    }

    /**
     * @return density (int)
     * @since 1.0
     */
    public static float density() {
        return ApplicationHelper.resources().getDisplayMetrics().density;
    }

    /**
     * @return density dpi (float)
     * @since 1.0
     */
    public static int densityDpi() {
        return ApplicationHelper.resources().getDisplayMetrics().densityDpi;
    }

    /**
     * @return height in pixels
     * @since 1.0
     */
    public static int height() {
        return ApplicationHelper.resources().getDisplayMetrics().heightPixels;
    }

    /**
     * Get Android's StatusBar standard height.
     *
     * @param window of the device/application
     * @return height (in pixels)
     * @since 1.0
     */
    public static int statusBarHeight(@NotNull final Window window) {
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * Get Android's ActionBar standard height.
     *
     * @param window of the device/application
     * @return height (in pixels)
     * @since 1.0
     */
    public static int actionBarHeight(@NotNull final Window window) {
        int top = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return (top - statusBarHeight(window));
    }

    /**
     * Get device's screen width.
     *
     * @return width in pixels
     * @since 1.0
     */
    public static int width() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Get device's screen orientation (in degrees).
     *
     * @return orientation degrees (0-360)
     * @see me.shkschneider.skeleton.helper.ActivityHelper#portrait()
     * @see me.shkschneider.skeleton.helper.ActivityHelper#landscape()
     * @since 1.0
     */
    public static int orientation() {
        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        if (display == null) {
            LogHelper.warning("Display was NULL");
            return 0;
        }

        return display.getRotation();
    }

    /**
     * @param dp to convert
     * @return pixels
     * @since 1.0
     */
    public static int pixelsFromDp(final float dp) {
        if (dp <= 0F) {
            LogHelper.warning("Dp was too low");
            return 0;
        }
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SkeletonApplication.CONTEXT.getResources().getDisplayMetrics()));
    }

}
