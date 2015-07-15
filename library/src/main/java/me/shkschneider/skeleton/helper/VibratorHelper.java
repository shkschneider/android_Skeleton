package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.os.Vibrator;
import android.support.annotation.RequiresPermission;

public class VibratorHelper {

    protected VibratorHelper() {
        // Empty
    }

    public static boolean hasVibrator() {
        final Vibrator vibrator = SystemServices.vibrator();
        return vibrator.hasVibrator();
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public static boolean vibrate(final long[] durations, final boolean repeat) {
        final Vibrator vibrator = SystemServices.vibrator();
        if (! hasVibrator()) {
            LogHelper.w("No vibrator");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public static boolean vibrate(final long[] durations) {
        return vibrate(durations, false);
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public static boolean vibrate(final long duration) {
        return vibrate(new long[] { duration }, false);
    }

}
