package me.shkschneider.skeleton.helper

import android.os.AsyncTask
import android.os.Handler
import androidx.annotation.IntRange

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object RunnableHelper {

    fun async(runnable: Runnable) {
        AsyncTask.execute(runnable)
    }

    // <http://stackoverflow.com/a/28581385>
    fun post(runnable: Runnable) {
        Handler().post(runnable)
    }

    fun delay(runnable: Runnable, @IntRange(from = 0) amount: Int, timeUnit: TimeUnit) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, amount.toLong(), timeUnit)
    }

    fun repeat(runnable: Runnable, @IntRange(from = 0) amount: Int, timeUnit: TimeUnit) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, amount.toLong(), timeUnit)
    }

}
