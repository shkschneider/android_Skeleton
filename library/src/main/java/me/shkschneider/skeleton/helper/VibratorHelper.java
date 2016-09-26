package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public class VibratorHelper {

    public static final long[] DEFAULT_DURATION = new long[] { 1000, 1000, 1000, 1000, 1000 };

    protected VibratorHelper() {
        // Empty
    }

    public static boolean hasVibrator(@NonNull final Context context) {
        final Vibrator vibrator = SystemServices.vibrator(context);
        return vibrator.hasVibrator();
    }

    public static boolean vibrate(@NonNull final Context context, final long[] durations, final boolean repeat) {
        final Vibrator vibrator = SystemServices.vibrator(context);
        if (! hasVibrator(context)) {
            LogHelper.warning("No vibrator");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    public static boolean vibrate(@NonNull final Context context, @IntRange(from=1) final long duration, final boolean repeat) {
        return vibrate(context, new long[] { duration }, repeat);
    }

    public static boolean vibrate(@NonNull final Context context, @IntRange(from=1) final long duration) {
        return vibrate(context, duration, false);
    }

    public static boolean vibrate(@NonNull final Context context) {
        return vibrate(context, DEFAULT_DURATION, false);
    }

}
