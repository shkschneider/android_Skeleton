package me.shkschneider.skeleton.helper

import android.content.res.Resources
import android.support.annotation.FloatRange
import android.util.DisplayMetrics
import android.util.TypedValue

object Metrics {

    fun displayMetrics(): DisplayMetrics {
        return Resources.getSystem().displayMetrics
    }

    fun density(): Float {
        return displayMetrics().density
    }

    fun dpi(): Int {
        return displayMetrics().densityDpi
    }

    fun dpFromPixels(@FloatRange(from = 0.0) px: Float): Int {
        return (px / density()).toInt()
    }

    fun pixelsFromDp(@FloatRange(from = 0.0) dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics()).toInt()
    }

    fun pixelsFromSp(@FloatRange(from = 0.0) sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics()).toInt()
    }

}
