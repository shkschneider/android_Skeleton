package me.sdk;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {

    public static void delayedRunnable(final Runnable runnable, final int amount, TimeUnit timeUnit) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, amount, timeUnit);
    }

}
