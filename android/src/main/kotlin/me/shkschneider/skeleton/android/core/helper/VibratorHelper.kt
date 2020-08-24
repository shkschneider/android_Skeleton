package me.shkschneider.skeleton.helper

import android.Manifest
import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.helperx.SystemServices

object VibratorHelper {

    private val DEFAULT_DURATION = longArrayOf(1000, 1000, 1000, 1000, 1000)
    private const val DO_REPEAT = 0
    private const val DO_NOT_REPEAT = -1

    val has: Boolean
        get() = SystemServices.vibrator()?.hasVibrator() ?: false

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(durations: LongArray = DEFAULT_DURATION, repeat: Boolean = false) {
        if (Build.VERSION.SDK_INT >= 26) {
            SystemServices.vibrator()?.vibrate(VibrationEffect.createWaveform(durations, if (repeat) DO_REPEAT else DO_NOT_REPEAT))
        } else {
            @Suppress("DEPRECATION")
            SystemServices.vibrator()?.vibrate(durations, if (repeat) DO_REPEAT else DO_NOT_REPEAT)
        }
    }

}
