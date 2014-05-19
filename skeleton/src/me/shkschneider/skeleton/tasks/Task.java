package me.shkschneider.skeleton.tasks;

import android.os.AsyncTask;

public class Task {

    public void run(final Runnable runnable, final Callback taskCallback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... v) {
                runnable.run();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);

                taskCallback.taskCallback();
            }

        }.execute();
    }

    // No cancel() method, as Thread.stop() is deprecated because "unsafe".

    public static interface Callback {

        public void taskCallback();

    }

}
