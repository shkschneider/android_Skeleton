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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import me.shkschneider.skeleton.android.LogHelper;
import me.shkschneider.skeleton.java.TimeHelper;

@SuppressWarnings("unused")
public class Tasks {

    private Activity mActivity;
    private final List<Callable<Object>> mTasks;
    private Callback mCallback;
    private Boolean mRunning;
    private Long mDuration;

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

    public void queue(final Runnable runnable) {
        if (runnable == null) {
            LogHelper.w("Runnable was NULL");
            return ;
        }

        synchronized(mTasks) {
            mTasks.add(Executors.callable(runnable));
            mTasks.notify();
        }
    }

    public void run(final Callback callback) {
        if (mRunning) {
            LogHelper.w("Task was running");
            return ;
        }

        mCallback = callback;
        mDuration = TimeHelper.millitimestamp();
        mRunning = true;
        try {
            Executors.newFixedThreadPool(mTasks.size()).invokeAll(mTasks);
        }
        catch (InterruptedException e) {
            LogHelper.w("InterruptedException: " + e.getMessage());
        }
        mRunning = false;
        mDuration = (TimeHelper.millitimestamp() - mDuration);
        callback();
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

        if (mActivity == null) {
            mCallback.tasksCallback(mDuration);
        }
        else {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mCallback.tasksCallback(mDuration);
                }

            });
        }
    }

    public interface Callback {

        public void tasksCallback(final Long duration);

    }

}
