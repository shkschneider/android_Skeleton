package me.shkschneider.skeleton.helper;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.FutureTask;

public class ThreadHelper {

    protected ThreadHelper() {
        // Empty
    }

    public static boolean uiThread() {
        return  (Looper.getMainLooper() == Looper.myLooper());
    }

    // android.support.annotation.UiThread
    public static void foregroundThread(@NonNull final Runnable runnable) {
        if (uiThread()) {
            runnable.run();
            return;
        }
        HandlerHelper.main().post(runnable);
    }

    public static FutureTask backgroundThread(@NonNull final Runnable runnable) {
        final FutureTask<Void> futureTask = new FutureTask<>(new Runnable() {
            @Override
            public void run() {
                new Thread(runnable).start();
            }
        }, null);
        futureTask.run();
        return futureTask;
    }

}
