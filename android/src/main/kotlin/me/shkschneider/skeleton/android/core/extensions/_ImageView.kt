package me.shkschneider.skeleton.android.core.extensions

import android.widget.ImageView
import androidx.annotation.ColorInt

fun ImageView.tint(@ColorInt color: Int) {
    setColorFilter(color)
}
