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

import java.util.LinkedList;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class Tasks {

    private final LinkedList<Runnable> mTasks;
    private Boolean mRunning;

    public Tasks() {
        mTasks = new LinkedList<Runnable>();
        mRunning = false;
    }

    public Boolean queue(final Runnable runnable) {
        if (! mRunning) {
            if (runnable != null) {
                synchronized(mTasks) {
                    mTasks.addLast(runnable);
                    mTasks.notify();
                }
                return true;
            }
            else {
                LogHelper.w("Runnable was NULL");
            }
        }
        else {
            LogHelper.d("Task was running");
        }
        return false;
    }

    protected Runnable next() {
        synchronized(mTasks) {
            if (mTasks.isEmpty()) {
                mRunning = false;
            }
            return mTasks.removeFirst();
        }
    }

    private void myRun() {
        while (mRunning) {
            final Runnable runnable = next();
            try {
                runnable.run();
                Thread.yield();
            }
            catch (Throwable t) {
                LogHelper.e("Throwable: " + t.getMessage());
            }
        }
    }

    public Boolean run() {
        if (! mRunning) {
            final Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    myRun();
                }

            });
            thread.setDaemon(true);
            mRunning = true;
            thread.start();
            return true;
        }
        else {
            LogHelper.d("Task was running");
        }
        return false;
    }

    public Boolean running() {
        return mRunning;
    }

}
