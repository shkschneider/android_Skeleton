package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

@SuppressWarnings("unused")
public class Vibrator {

    @SuppressLint("NewApi")
    private static void vibrateNew(final android.os.Vibrator vibrator, final long duration) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(duration);
        }
        else {
            Log.d("Vibrator cannot vibrate");
        }
    }

    @SuppressLint("NewApi")
    private static void vibrateNew(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(durations, (repeat ? 0 : -1));
        }
        else {
            Log.d("Vibrator cannot vibrate");
        }
    }

    private static void vibrateOld(final android.os.Vibrator vibrator, final long duration) {
        vibrator.vibrate(duration);
    }

    private static void vibrateOld(final android.os.Vibrator vibrator, final long[] durations, final Boolean repeat) {
        vibrator.vibrate(durations, (repeat ? 0 : -1));
    }

    public static void vibrate(final Context context, final long duration) {
        final android.os.Vibrator vibrator = (android.os.Vibrator) Skeleton.System.systemService(context, Skeleton.System.SYSTEM_SERVICE_VIBRATOR);
        if (vibrator != null) {
            if (Skeleton.Android.api() < Build.VERSION_CODES.HONEYCOMB) {
                vibrateOld(vibrator, duration);
            }
            else {
                vibrateNew(vibrator, duration);
            }
        }
        else {
            Log.w("Vibrator was NULL");
        }
    }

    public static void vibrate(final Context context, final long[] durations, final Boolean repeat) {
        final android.os.Vibrator vibrator = (android.os.Vibrator) Skeleton.System.systemService(context, Skeleton.System.SYSTEM_SERVICE_VIBRATOR);
        if (vibrator != null) {
            if (Skeleton.Android.api() < Build.VERSION_CODES.HONEYCOMB) {
                vibrateOld(vibrator, durations, repeat);
            }
            else {
                vibrateNew(vibrator, durations, repeat);
            }
        }
        else {
            Log.w("Vibrator was NULL");
        }
    }

}
