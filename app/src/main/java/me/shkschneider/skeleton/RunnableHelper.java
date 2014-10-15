package me.shkschneider.skeleton;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class RunnableHelper {

    public static void delay(final Runnable runnable, final int amount, TimeUnit timeUnit) {
        new Handler().postDelayed(runnable, timeUnit.toMillis(amount));
    }

}
