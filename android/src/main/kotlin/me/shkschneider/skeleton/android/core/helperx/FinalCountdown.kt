package me.shkschneider.skeleton.android.core.helperx

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

/**
 * It's the final countdown
 * The final countdown
 */
open class FinalCountdown(
        private val timer: Long = TimeUnit.SECONDS.toMillis(60),
        private val ticker: Long = TimeUnit.SECONDS.toMillis(1)
) {

    fun run(callback: Callback) {
        object: CountDownTimer(timer, ticker) {
            override fun onTick(millisUntilFinished: Long) {
                callback.onCountdownTick(millisUntilFinished)
            }
            override fun onFinish() {
                callback.onCountdownFinish()
            }
        }.start()
    }

    // TODO SkeletonReceiver
    interface Callback {

        fun onCountdownTick(millisUntilFinished: Long)
        fun onCountdownFinish()

    }

}