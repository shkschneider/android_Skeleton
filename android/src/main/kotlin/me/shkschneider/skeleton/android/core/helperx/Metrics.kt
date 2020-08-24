package me.shkschneider.skeleton.android.core.helperx

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.FloatRange
import me.shkschneider.skeleton.android.core.helper.ContextHelper

object Metrics {

    fun displayMetrics(): DisplayMetrics =
        ContextHelper.applicationContext().resources.displayMetrics

    fun density(): Float =
        displayMetrics().density

    fun dpi(): Int =
        displayMetrics().densityDpi

    fun dpFromPixels(@FloatRange(from = 0.0) px: Float): Int =
        (px / density()).toInt()

    fun pixelsFromDp(@FloatRange(from = 0.0) dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics()).toInt()

    fun pixelsFromSp(@FloatRange(from = 0.0) sp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics()).toInt()

}
