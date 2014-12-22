package me.shkschneider.skeleton.tasks;

import android.os.Handler;

import me.shkschneider.skeleton.helpers.LogHelper;

public class RepeatableRunnable extends RetryableRunnable {

    @Override
    public void run() {
        // Empty
    }

    public boolean repeat(final Handler handler, final long milliseconds) {
        if (handler == null) {
            LogHelper.warning("Handler was NULL");
            return false;
        }
        if (milliseconds < 0) {
            LogHelper.warning("Delay was NEGATIVE");
            return false;
        }

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                handler.postDelayed(this, milliseconds);
                retry();
            }

        };
        return handler.postDelayed(runnable, milliseconds);
    }

}
