package me.shkschneider.skeleton.helper;

import android.os.Vibrator;
import android.support.annotation.IntRange;

public class VibratorHelper {

    public static final long DEFAULT_DURATION = 500L;

    protected VibratorHelper() {
        // Empty
    }

    public static boolean hasVibrator() {
        final Vibrator vibrator = SystemServices.vibrator();
        return vibrator.hasVibrator();
    }

    public static boolean vibrate(final long[] durations, final boolean repeat) {
        final Vibrator vibrator = SystemServices.vibrator();
        if (! hasVibrator()) {
            LogHelper.warning("No vibrator");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    public static boolean vibrate(@IntRange(from=1) final long duration, final boolean repeat) {
        return vibrate(new long[] { duration }, repeat);
    }

    public static boolean vibrate(@IntRange(from=1) final long duration) {
        return vibrate(duration, false);
    }

}
