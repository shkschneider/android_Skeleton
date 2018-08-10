package me.shkschneider.skeleton.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v7.graphics.Palette

object PaletteHelper {

    @ColorInt
    private val DEFAULT_COLOR = Color.TRANSPARENT

    private operator fun get(bitmap: Bitmap): Palette {
        return Palette.Builder(bitmap).generate()
    }

    fun vibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getVibrantColor(DEFAULT_COLOR)
    }

    fun lightVibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getLightVibrantColor(DEFAULT_COLOR)
    }

    fun darkVibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getDarkVibrantColor(DEFAULT_COLOR)
    }

    fun mutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getMutedColor(DEFAULT_COLOR)
    }

    fun lightMutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getLightMutedColor(DEFAULT_COLOR)
    }

    fun darkMutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getDarkMutedColor(DEFAULT_COLOR)
    }

}
