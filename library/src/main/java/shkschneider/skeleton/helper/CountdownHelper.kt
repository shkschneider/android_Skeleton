package me.shkschneider.skeleton.helper

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class CountdownHelper {

    private val timer: Long
    private val ticker: Long

    constructor(timer: Long = TimeUnit.SECONDS.toMillis(60), ticker: Long = TimeUnit.SECONDS.toMillis(1)) {
        this.timer = timer
        this.ticker = ticker
    }

    fun run(callback: Callback) {
        object : CountDownTimer(timer, ticker) {
            override fun onTick(millisUntilFinished: Long) {
                callback.onCountdownTick(millisUntilFinished)
            }
            override fun onFinish() {
                callback.onCountdownFinish()
            }
        }.start()
    }

    interface Callback {

        fun onCountdownTick(millisUntilFinished: Long)
        fun onCountdownFinish()

    }

}