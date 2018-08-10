package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.ContentLoadingProgressBar
import android.util.AttributeSet

// android:progressTint=""
// style="?android:attr/progressBarStyleLarge"
class LoadingProgressBar : ContentLoadingProgressBar {

    init {
        isIndeterminate = true
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    fun color(@ColorInt color: Int) {
        if (isIndeterminate) {
            DrawableCompat.setTintList(indeterminateDrawable, ColorStateList.valueOf(color))
        } else {
            DrawableCompat.setTintList(progressDrawable, ColorStateList.valueOf(color))
        }
    }

}
