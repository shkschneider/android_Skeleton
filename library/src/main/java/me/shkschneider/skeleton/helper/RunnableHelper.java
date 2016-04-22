package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableHelper {

    protected RunnableHelper() {
        // Empty
    }

    public static void delay(@NonNull final Runnable runnable, @IntRange(from=0) final int amount, @NonNull final TimeUnit timeUnit) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, amount, timeUnit);
    }

    public static void repeat(@NonNull final Runnable runnable, @IntRange(from=0) final int amount, @NonNull final TimeUnit timeUnit) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, amount, timeUnit);
    }

    public static void runOnMainLooper(@NonNull final Runnable runnable) {
        final boolean isOnMainLooper = (Looper.getMainLooper() == Looper.myLooper());
        if (isOnMainLooper) {
            runnable.run();
        }
        else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    // @MainThread
    public static void runOnUiThread(@NonNull final Activity activity, @NonNull final Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

}
