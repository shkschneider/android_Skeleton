/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.task;

import android.app.Activity;

import java.util.LinkedList;

import me.shkschneider.skeleton.android.LogHelper;
import me.shkschneider.skeleton.java.TimeHelper;

@SuppressWarnings("unused")
public class QueuedTasks {

    private Activity mActivity;
    private final LinkedList<Runnable> mRunnables;
    private Callback mCallback;
    private Boolean mRunning;
    private Long mDuration;

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

    public void queue(final Runnable runnable) {
        if (mRunning) {
            LogHelper.d("Task was running");
            return ;
        }

        if (runnable == null) {
            LogHelper.w("Runnable was NULL");
            return ;
        }

        synchronized(mRunnables) {
            mRunnables.addLast(runnable);
            mRunnables.notify();
        }
    }

    protected Runnable next() {
        synchronized(mRunnables) {
            if (mRunnables.isEmpty()) {
                mRunning = false;
                mDuration = (TimeHelper.millitimestamp() - mDuration);
                callback();
                return null;
            }
            return mRunnables.removeFirst();
        }
    }

    private void myRun() {
        while (mRunning) {
            final Runnable runnable = next();
            if (runnable == null) {
                LogHelper.w("Runnable was NULL");
                break ;
            }

            try {
                runnable.run();
                Thread.yield();
            }
            catch (Throwable t) {
                LogHelper.e("Throwable: " + t.getMessage());
            }
        }
    }

    public void run(final Callback callback) {
        if (mRunning) {
            LogHelper.d("Task was running");
            return ;
        }

        mCallback = callback;
        mDuration = TimeHelper.millitimestamp();
        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                myRun();
            }

        });
        thread.setDaemon(true);
        mRunning = true;
        thread.start();
    }

    public void run() {
        run(null);
    }

    public Boolean running() {
        return mRunning;
    }

    public Long duration() {
        return (mRunning ? 0L : mDuration);
    }

    // TODO: cancel

    private void callback() {
        if (mCallback == null) {
            LogHelper.d("Callback was NULL");
            return ;
        }

        if (mActivity != null) {
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

    public interface Callback {

        public void queuedTasksCallback(final Long duration);

    }

}
