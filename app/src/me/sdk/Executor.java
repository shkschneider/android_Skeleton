package me.sdk;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {

    public static void delayedRunnable(final Runnable runnable, final int amount, TimeUnit timeUnit) {
        Executors.newScheduledThreadPool(1).schedule(runnable, amount, timeUnit);
    }

}
