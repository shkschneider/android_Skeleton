package me.shkschneider.skeleton.helper

import android.os.Looper

import java.util.concurrent.FutureTask

object ThreadHelper {

    fun mainThread(): Boolean {
        return Looper.getMainLooper() == Looper.myLooper()
    }

    @Deprecated("UIThread is MainThread.", ReplaceWith("mainThread()"))
    fun uiThread(): Boolean {
        return mainThread()
    }

    // android.support.annotation.UiThread
    fun foregroundThread(runnable: Runnable) {
        if (mainThread()) {
            runnable.run()
            return
        }
        HandlerHelper.main().post(runnable)
    }

    fun backgroundThread(runnable: Runnable): FutureTask<*> {
        val futureTask = FutureTask<Void>(Runnable { Thread(runnable).start() }, null)
        futureTask.run()
        return futureTask
    }

}
