package me.shkschneider.skeleton.android.widget

import android.widget.ImageView
import androidx.annotation.ColorInt

fun ImageView.tint(@ColorInt color: Int) {
    setColorFilter(color)
}
