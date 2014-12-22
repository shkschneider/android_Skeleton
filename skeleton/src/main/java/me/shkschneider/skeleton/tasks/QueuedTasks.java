package me.shkschneider.skeleton.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.helpers.DateTimeHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import java.util.LinkedList;

public class QueuedTasks {

    private Activity mActivity;
    // http://docs.oracle.com/javase/6/docs/api/java/util/LinkedList.html
    private final LinkedList<Runnable> mRunnables;
    private Callback mCallback;
    private boolean mRunning;
    private long mDuration;

    public QueuedTasks(final Activity activity) {
        mActivity = activity;
        mRunnables = new LinkedList<Runnable>();
        mCallback = null;
        mRunning = false;
        mDuration = 0L;
    }

    public QueuedTasks() {
        this(null);
    }

    public void queue(@NonNull final Runnable runnable) {
        // thread-safe
        synchronized(mRunnables) {
            mRunnables.addLast(runnable);
            mRunnables.notify();
        }
    }

    protected Runnable next() {
        // thread-safe
        synchronized(mRunnables) {
            if (mRunnables.isEmpty()) {
                mRunning = false;
                mDuration = (DateTimeHelper.millitimestamp() - mDuration);
                callback();
                return null;
            }

            return mRunnables.removeFirst();
        }
    }

    private void myRun() {
        while (mRunning) {
            final Runnable runnable = next();
            try {
                runnable.run();
                Thread.yield();
            }
            catch (final Throwable t) {
                LogHelper.wtf(t);
            }
        }
    }

    public void run(final Callback callback) {
        if (mRunning) {
            LogHelper.debug("Task was running");
            return ;
        }

        mCallback = callback;
        mDuration = DateTimeHelper.millitimestamp();
        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                myRun();
            }

        });
        thread.interrupt();
        thread.setDaemon(true);
        mRunning = true;
        thread.start();
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

    private void callback() {
        if (mCallback == null) {
            LogHelper.info("Receiver was NULL");
            return ;
        }

        if (mActivity != null) {
            // ui-thread-safe
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mCallback.queuedTasksCallback(mDuration);
                }

            });
        }
        else {
            mCallback.queuedTasksCallback(mDuration);
        }
    }

    public int size() {
        return mRunnables.size();
    }

    public static interface Callback {

        public void queuedTasksCallback(final long duration);

    }

}
