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
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, 1, TimeUnit.SECONDS);
    }

    public static void repeat(@NonNull final Runnable runnable, @IntRange(from=0) final int amount, @NonNull final TimeUnit timeUnit) {
        new Thread(new Runnable() {

                private Handler mHandler = new Handler();

                @Override
                public void run() {
                    runnable.run();
                    mHandler.postDelayed(this, timeUnit.toMillis(amount));
                }

        }).start();
    }

    public static void runOnMainLooper(@NonNull final Runnable runnable) {
        final boolean isOnMainLooper = (Looper.getMainLooper() == Looper.myLooper());
        if (isOnMainLooper) {
            runnable.run();
        }
        else {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(runnable);
        }
    }

    // @MainThread
    public static void runOnUiThread(@NonNull final Activity activity, @NonNull final Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

}
