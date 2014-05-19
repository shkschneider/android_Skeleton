package me.shkschneider.skeleton.helpers;

import android.annotation.TargetApi;
import android.os.Vibrator;

public class VibratorHelper {

    @TargetApi(AndroidHelper.API_11)
    private static boolean hasVibratorNew() {
        final Vibrator vibrator = (Vibrator) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        return vibrator.hasVibrator();
    }

    public static boolean hasVibrator() {
        if (AndroidHelper.api() >= AndroidHelper.API_11) {
            return hasVibratorNew();
        }
        else {
            // Assume true -- not a problem
            return true;
        }
    }

    public static boolean vibrate(final long[] durations, final boolean repeat) {
        final Vibrator vibrator = (Vibrator) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_VIBRATOR);
        if (! hasVibrator()) {
            LogHelper.warning("No vibrator");
            return false;
        }

        vibrator.vibrate(durations, (repeat ? 0 : -1));
        return true;
    }

    public static boolean vibrate(final long[] durations) {
        return vibrate(durations, false);
    }

    public static boolean vibrate(final long duration) {
        return vibrate(new long[] { duration }, false);
    }

}
