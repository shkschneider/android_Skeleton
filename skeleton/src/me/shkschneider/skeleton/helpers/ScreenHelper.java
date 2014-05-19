package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.shkschneider.skeleton.SkeletonApplication;

import org.jetbrains.annotations.NotNull;

public class ScreenHelper {

    public static final int DENSITY_LDPI = 120;
    public static final int DENSITY_MDPI = 160;
    public static final int DENSITY_TVDPI = 213; // MDPI * 1.33
    public static final int DENSITY_HDPI = 240;
    public static final int DENSITY_XHDPI = 320;
    public static final int DENSITY_XXHDPI = 480;
    public static final int DENSITY_XXXHDPI = 640;
    public static final int DENSITY_TV = 213;

    public static boolean wakeLock(@NotNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    public static boolean on() {
        return ((PowerManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_POWER_SERVICE)).isScreenOn();
    }

    public static int density() {
        return ApplicationHelper.resources().getDisplayMetrics().densityDpi;
    }

    public static int height() {
        return ApplicationHelper.resources().getDisplayMetrics().heightPixels;
    }

    public static int statusBarHeight(@NotNull final Window window) {
        final Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int actionBarHeight(@NotNull final Window window) {
        int top = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return top - statusBarHeight(window);
    }

    public static int width() {
        return SkeletonApplication.CONTEXT.getResources().getDisplayMetrics().widthPixels;
    }

    public static int orientation() {
        final WindowManager windowManager = (WindowManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_WINDOW_SERVICE);
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

    public static Bitmap screenshot(@NotNull final View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        view.buildDrawingCache();

        if (view.getDrawingCache() == null) {
            LogHelper.warning("DrawingCache was NULL");
            return null;
        }

        final Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return screenshot;
    }

}
