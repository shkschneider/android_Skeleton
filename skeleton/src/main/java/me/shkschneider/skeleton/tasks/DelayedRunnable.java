package me.shkschneider.skeleton.tasks;

import android.os.Handler;

import me.shkschneider.skeleton.helpers.LogHelper;

public class DelayedRunnable implements Runnable {

    @Override
    public void run() {
        // Empty
    }

    public boolean delay(final Handler handler, final long milliseconds) {
        if (handler == null) {
            LogHelper.warning("Handler was NULL");
            return false;
        }
        if (milliseconds < 0) {
            LogHelper.warning("Delay was NEGATIVE");
            return false;
        }

        return handler.postDelayed(this, milliseconds);
    }

}
