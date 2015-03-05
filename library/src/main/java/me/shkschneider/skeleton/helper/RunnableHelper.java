package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

public class RunnableHelper {

    public static void delay(@NonNull final Runnable runnable, final int amount, final TimeUnit timeUnit) {
        new Handler().postDelayed(runnable, timeUnit.toMillis(amount));
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

    public static void runOnUiThread(@NonNull final Activity activity, @NonNull final Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

}
