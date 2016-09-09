package me.shkschneider.skeleton.helper;

import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableHelper {

    protected RunnableHelper() {
        // Empty
    }

    // <http://stackoverflow.com/a/28581385>
    public static void post(@NonNull final Runnable runnable) {
        new Handler().post(runnable);
    }

    public static void delay(@NonNull final Runnable runnable, @IntRange(from=0) final int amount, @NonNull final TimeUnit timeUnit) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, amount, timeUnit);
    }

    public static void repeat(@NonNull final Runnable runnable, @IntRange(from=0) final int amount, @NonNull final TimeUnit timeUnit) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, amount, timeUnit);
    }

}
