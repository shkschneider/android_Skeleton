package me.shkschneider.skeleton;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.helper.LogHelper;

/**
 * @TODO
 */
public class Executor {

    public static void delayRunnable(final Runnable runnable, final int amount, final TimeUnit timeUnit) {
        if (runnable == null) {
            LogHelper.warning("Runnable was NULL");
            return ;
        }
        final Handler handler = new Handler();
        handler.postDelayed(runnable, (timeUnit.toSeconds(amount) * 1000));
    }
}
