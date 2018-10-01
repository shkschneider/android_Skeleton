package me.shkschneider.skeleton.helper

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Surface
import android.view.Window
import android.view.WindowManager
import androidx.annotation.FloatRange
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.extensions.then

object ScreenHelper {

    const val DENSITY_LDPI = 120
    const val DENSITY_MDPI = 160
    const val DENSITY_TVDPI = 213 // MDPI * 1.33
    const val DENSITY_HDPI = 240
    const val DENSITY_XHDPI = 320
    const val DENSITY_XXHDPI = 480
    const val DENSITY_XXXHDPI = 640

    const val BRIGHTNESS_RESET = (-1.1).toFloat()
    const val BRIGHTNESS_MIN = 0.1.toFloat()
    const val BRIGHTNESS_MAX = 1.1.toFloat()

    const val ROTATION_0 = Surface.ROTATION_0
    const val ROTATION_90 = Surface.ROTATION_90
    const val ROTATION_180 = Surface.ROTATION_180
    const val ROTATION_270 = Surface.ROTATION_270

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    fun wakeLock(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // Does NOT @RequiresPermission(Manifest.permission.WAKE_LOCK)
    fun wakeUnlock(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    fun on(): Boolean? {
        val powerManager = SystemServices.powerManager()
        if (Build.VERSION.SDK_INT >= 20) {
            return powerManager?.isInteractive
        } else {
            @Suppress("DEPRECATION")
            return powerManager?.isScreenOn
        }
    }

    fun brightness(window: Window): Float {
        return window.attributes.screenBrightness
    }

    fun brightness(window: Window, @FloatRange(from = BRIGHTNESS_RESET.toDouble(), to = BRIGHTNESS_MAX.toDouble()) brightness: Float) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = (brightness < BRIGHTNESS_MIN) then BRIGHTNESS_RESET ?: brightness
        window.attributes = layoutParams
    }

    // <https://github.com/eyeem/deviceinfo>
    fun inches(): Float {
        val windowManager = SystemServices.windowManager()
        val displayMetrics = DisplayMetrics()
        val display = windowManager?.defaultDisplay
        display?.getMetrics(displayMetrics)
        val point = Point(1, 1)
        if (Build.VERSION.SDK_INT >= 17) {
            display?.getRealSize(point)
        } else {
            display?.getSize(point)
        }
        val w = point.x / displayMetrics.xdpi
        val h = point.y / displayMetrics.ydpi
        val inches = Math.sqrt(Math.pow(w.toDouble(), 2.0) + Math.pow(h.toDouble(), 2.0)).toFloat()
        Logger.verbose("inches: $inches\"")
        return inches
    }

    fun height(): Int {
        return Metrics.displayMetrics().heightPixels
    }

    fun width(): Int {
        return Metrics.displayMetrics().widthPixels
    }

    fun statusBarHeight(): Int {
        val resId = ApplicationHelper.resources().getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            return ApplicationHelper.resources().getDimensionPixelSize(resId)
        }
        return ApplicationHelper.resources().getDimensionPixelSize(R.dimen.statusBar)
    }

    fun navigationBarHeight(): Int {
        val resId = ApplicationHelper.resources().getIdentifier("navigation_bar_height", "dimen", "android")
        if (resId > 0) {
            return ApplicationHelper.resources().getDimensionPixelSize(resId)
        }
        return ApplicationHelper.resources().getDimensionPixelSize(R.dimen.statusBar)
    }

    fun orientation(context: Context): Int {
        return context.resources.configuration.orientation
    }

    fun rotation(): Int? {
        return SystemServices.windowManager()?.defaultDisplay?.rotation
    }

}
