package me.shkschneider.skeleton.ui

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ContentLoadingProgressBar
import android.util.AttributeSet
import androidx.annotation.ColorInt

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
