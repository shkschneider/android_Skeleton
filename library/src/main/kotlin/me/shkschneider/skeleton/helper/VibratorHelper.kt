package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.support.annotation.RequiresPermission

object VibratorHelper {

    private val DEFAULT_DURATION = longArrayOf(1000, 1000, 1000, 1000, 1000)
    private val DO_REPEAT = 0
    private val DO_NOT_REPEAT = -1

    fun has(): Boolean {
        val vibrator = SystemServices.vibrator()
        return vibrator != null && vibrator.hasVibrator()
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(durations: LongArray, repeat: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SystemServices.vibrator()?.vibrate(VibrationEffect.createWaveform(durations, if (repeat) DO_REPEAT else DO_NOT_REPEAT))
        } else {
            SystemServices.vibrator()?.vibrate(durations, if (repeat) DO_REPEAT else DO_NOT_REPEAT)
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate() {
        vibrate(DEFAULT_DURATION)
    }

}
