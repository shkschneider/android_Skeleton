package me.shkschneider.skeleton;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

public class Executor {

    public static void delayRunnable(@NonNull final Runnable runnable, final int amount, final TimeUnit timeUnit) {
        new Handler().postDelayed(runnable, timeUnit.toMillis(amount));
//        Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//
//            @Override
//            public void run() {
//                runnable.run();
//            }
//
//        }, amount, timeUnit);
    }

}
