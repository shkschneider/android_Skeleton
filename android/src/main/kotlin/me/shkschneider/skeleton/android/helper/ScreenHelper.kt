package me.shkschneider.skeleton.android.helper

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Surface
import android.view.Window
import android.view.WindowManager
import androidx.annotation.FloatRange
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.util.Metrics
import me.shkschneider.skeleton.android.provider.SystemServices
import me.shkschneider.skeleton.kotlin.log.Logger
import kotlin.math.pow
import kotlin.math.sqrt

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
        return powerManager?.isInteractive
    }

    fun brightness(window: Window): Float {
        return window.attributes.screenBrightness
    }

    fun brightness(window: Window, @FloatRange(from = BRIGHTNESS_RESET.toDouble(), to = BRIGHTNESS_MAX.toDouble()) brightness: Float) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = if (brightness < BRIGHTNESS_MIN) BRIGHTNESS_RESET else brightness
        window.attributes = layoutParams
    }

    // <https://github.com/eyeem/deviceinfo>
    fun inches(): Float {
        val windowManager = SystemServices.windowManager()
        val displayMetrics = DisplayMetrics()
        val display = windowManager?.defaultDisplay
        display?.getMetrics(displayMetrics)
        val point = Point(1, 1)
        display?.getRealSize(point)
        val w = point.x / displayMetrics.xdpi
        val h = point.y / displayMetrics.ydpi
        val inches = sqrt(w.toDouble().pow(2.0) + h.toDouble().pow(2.0)).toFloat()
        Logger.verbose("Inches: $inches\"")
        return inches
    }

    fun height(): Int {
        return Metrics.displayMetrics().heightPixels
    }

    fun width(): Int {
        return Metrics.displayMetrics().widthPixels
    }

    fun statusBarHeight(): Int {
        val resId = ApplicationHelper.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            return ApplicationHelper.resources.getDimensionPixelSize(resId)
        }
        return ApplicationHelper.resources.getDimensionPixelSize(R.dimen.sk_statusBar)
    }

    fun navigationBarHeight(): Int {
        val resId = ApplicationHelper.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resId > 0) {
            return ApplicationHelper.resources.getDimensionPixelSize(resId)
        }
        return ApplicationHelper.resources.getDimensionPixelSize(R.dimen.sk_statusBar)
    }

    fun orientation(context: Context): Int {
        return context.resources.configuration.orientation
    }

    fun rotation(): Int? {
        return SystemServices.windowManager()?.defaultDisplay?.rotation
    }

}