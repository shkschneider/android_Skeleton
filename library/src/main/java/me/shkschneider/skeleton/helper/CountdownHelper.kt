package me.shkschneider.skeleton.helper

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class CountdownHelper {

    private val _timer: Long
    private val _ticker: Long

    constructor(timer: Long = TimeUnit.SECONDS.toMillis(10), ticker: Long = TimeUnit.SECONDS.toMillis(1)) {
        _timer = timer
        _ticker = ticker
    }

    fun run(callback: Callback) {
        object : CountDownTimer(_timer, _ticker) {
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