package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.content.res.Resources
import android.support.annotation.FloatRange
import android.util.TypedValue
import android.view.Surface
import android.view.Window
import android.view.WindowManager

import me.shkschneider.skeleton.R

object ScreenHelper {

    const val DENSITY_LDPI = 120
    const val DENSITY_MDPI = 160
    const val DENSITY_TVDPI = 213 // MDPI * 1.33
    const val DENSITY_HDPI = 240
    const val DENSITY_XHDPI = 320
    const val DENSITY_XXHDPI = 480
    const val DENSITY_XXXHDPI = 640

    const val BRIGHTNESS_RESET = -1.0f
    const val BRIGHTNESS_MIN = 0.0f
    const val BRIGHTNESS_MAX = 1.0f

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

    @Suppress("DEPRECATION")
    @SuppressLint("NewApi")
    fun on(): Boolean? {
        val powerManager = SystemServices.powerManager()
        return if (AndroidHelper.api() >= AndroidHelper.API_20) {
            powerManager?.isInteractive
        } else {
            powerManager?.isScreenOn
        }
    }

    fun brightness(window: Window): Float {
        return window.attributes.screenBrightness
    }

    fun brightness(window: Window, @FloatRange(from = BRIGHTNESS_RESET.toDouble(), to = BRIGHTNESS_MAX.toDouble()) brightness: Float) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = if (brightness < BRIGHTNESS_MIN) BRIGHTNESS_RESET else brightness
        window.attributes = layoutParams
    }

    fun density(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    fun dpi(): Int {
        return Resources.getSystem().displayMetrics.densityDpi
    }

    fun height(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun width(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun statusBarHeight(): Int {
        return ApplicationHelper.resources().getDimension(R.dimen.statusBar).toInt()
    }

    fun rotation(): Int? {
        return SystemServices.windowManager()?.defaultDisplay?.rotation
    }

    fun dpFromPixels(@FloatRange(from = 0.0) px: Float): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pixelsFromDp(@FloatRange(from = 0.0) dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics).toInt()
    }

    fun pixelsFromSp(@FloatRange(from = 0.0) sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics).toInt()
    }

}
