package me.sdk;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class Executor {

    public static void delayedRunnable(final Runnable runnable, final int amount, TimeUnit timeUnit) {
        new Handler().postDelayed(runnable, timeUnit.toMillis(amount));
    }

}
