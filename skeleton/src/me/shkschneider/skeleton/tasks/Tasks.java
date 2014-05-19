package me.shkschneider.skeleton.tasks;

import android.app.Activity;

import me.shkschneider.skeleton.helpers.DateTimeHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class Tasks {

    private Activity mActivity;
    private final List<Callable<Object>> mTasks;
    private Callback mCallback;
    private boolean mRunning;
    private long mDuration;

    public Tasks(final Activity activity) {
        mActivity = activity;
        mTasks = new ArrayList<Callable<Object>>();
        mCallback = null;
        mRunning = false;
        mDuration = 0L;
    }

    public Tasks() {
        this(null);
    }

    public void queue(@NotNull final Runnable runnable) {
        // thread-safe
        synchronized(mTasks) {
            mTasks.add(Executors.callable(runnable));
            mTasks.notify();
        }
    }

    public void run(final Callback callback) {
        if (mRunning) {
            LogHelper.warning("Task was running");
            return ;
        }

        boolean success = false;
        mCallback = callback;
        mDuration = DateTimeHelper.millitimestamp();
        mRunning = true;
        try {
            Executors.newFixedThreadPool(mTasks.size()).invokeAll(mTasks);
            success = true;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        mRunning = false;
        mDuration = (DateTimeHelper.millitimestamp() - mDuration);

        callback(success);
    }

    public void run() {
        run(null);
    }

    public boolean running() {
        return mRunning;
    }

    public long duration() {
        return (mRunning ? 0L : mDuration);
    }

    private void callback(final boolean success) {
        if (mCallback == null) {
            LogHelper.info("Receiver was NULL");
            return ;
        }

        if (mActivity == null) {
            mCallback.tasksCallback(success, mDuration);
        }
        else {
            // ui-thread-safe
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mCallback.tasksCallback(success, mDuration);
                }

            });
        }
    }

    public static interface Callback {

        public void tasksCallback(final boolean success, final long duration);

    }

}
