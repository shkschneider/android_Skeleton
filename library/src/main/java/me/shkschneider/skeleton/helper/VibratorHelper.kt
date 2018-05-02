package me.shkschneider.skeleton.helper

import android.Manifest
import android.os.Build
import android.os.VibrationEffect
import android.support.annotation.RequiresPermission
import me.shkschneider.skeleton.extensions.then

object VibratorHelper {

    private val DEFAULT_DURATION = longArrayOf(1000, 1000, 1000, 1000, 1000)
    private val DO_REPEAT = 0
    private val DO_NOT_REPEAT = -1

    fun has(): Boolean {
        return SystemServices.vibrator()?.let { return it.hasVibrator() } ?: false
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(durations: LongArray = DEFAULT_DURATION, repeat: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SystemServices.vibrator()?.vibrate(VibrationEffect.createWaveform(durations, (repeat) then DO_REPEAT ?: DO_NOT_REPEAT))
        } else {
            @Suppress("DEPRECATION")
            SystemServices.vibrator()?.vibrate(durations, (repeat) then DO_REPEAT ?: DO_NOT_REPEAT)
        }
    }

}