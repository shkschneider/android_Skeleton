package me.shkschneider.skeleton.helper;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresPermission;

public class VibratorHelper {

    public static final long[] DEFAULT_DURATION = new long[] { 1000L, 1000L, 1000L, 1000L, 1000L };

    private static final int DO_REPEAT = 0;
    private static final int DO_NOT_REPEAT = -1;

    protected VibratorHelper() {
        // Empty
    }

    public static boolean has() {
        final Vibrator vibrator = SystemServices.vibrator();
        return (vibrator != null && vibrator.hasVibrator());
    }

    @SuppressWarnings("deprecation")
    @RequiresPermission(Manifest.permission.VIBRATE)
    public static void vibrate(final long[] durations, final boolean repeat) {
        if (AndroidHelper.api() >= AndroidHelper.API_26) {
            vibrate26(durations, repeat);
            return;
        }
        SystemServices.vibrator().vibrate(durations, (repeat ? DO_REPEAT : DO_NOT_REPEAT));
    }

    @TargetApi(AndroidHelper.API_26)
    @RequiresPermission(Manifest.permission.VIBRATE)
    public static void vibrate26(final long[] durations, final boolean repeat) {
        SystemServices.vibrator().vibrate(VibrationEffect.createWaveform(durations, (repeat ? DO_REPEAT : DO_NOT_REPEAT)));
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    public static void vibrate() {
        if (AndroidHelper.api() >= AndroidHelper.API_26) {
            vibrate26(DEFAULT_DURATION, false);
            return;
        }
        vibrate(DEFAULT_DURATION, false);
    }

    @TargetApi(AndroidHelper.API_26)
    @RequiresPermission(Manifest.permission.VIBRATE)
    public static void vibrate26() {
        vibrate26(DEFAULT_DURATION, false);
    }

}
