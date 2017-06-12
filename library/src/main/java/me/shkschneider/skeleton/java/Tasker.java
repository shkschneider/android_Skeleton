package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import me.shkschneider.skeleton.SkeletonReceiver;

// <http://stackoverflow.com/a/9458785>
public class Tasker {

    private ExecutorService mExecutorService;
    private Future mFuture;

    public Tasker() {
        mExecutorService = Executors.newSingleThreadExecutor();
        mFuture = null;
    }

    public boolean isDone() {
        return (mFuture != null && mFuture.isDone());
    }

    public boolean isCancelled() {
        return (mFuture != null && mFuture.isCancelled());
    }

    public void run(@NonNull final Task task) {
        mFuture = mExecutorService.submit(task);
    }

    public boolean cancel(final boolean mayInterruptIfRunning) {
        return (mFuture != null && mFuture.cancel(mayInterruptIfRunning));
    }

    // <http://stackoverflow.com/a/826283>
    public static class Task implements Runnable {

        private final Runnable mRunnable;
        private final SkeletonReceiver.Callback mCallback;

        public Task(@NonNull final Runnable runnable, final SkeletonReceiver.Callback callback) {
            mRunnable = runnable;
            mCallback = callback;
        }

        @Override
        public void run() {
            mRunnable.run();
            mCallback.onReceive(0, null);
        }

    }

}
