package me.shkschneider.skeleton.extensions.android

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

fun ImageView.tint(@ColorInt color: Int) {
    setColorFilter(color)
}
