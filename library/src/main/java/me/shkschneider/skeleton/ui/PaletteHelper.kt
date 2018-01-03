package me.shkschneider.skeleton.ui

import android.graphics.Bitmap
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette

import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.ContextHelper

object PaletteHelper {

    @ColorRes
    private val DEFAULT_COLOR = R.color.sk_android_transparent

    private operator fun get(bitmap: Bitmap): Palette {
        return Palette.Builder(bitmap).generate()
    }

    fun vibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

    fun lightVibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getLightVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

    fun darkVibrantColor(bitmap: Bitmap): Int {
        return get(bitmap).getDarkVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

    fun mutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

    fun lightMutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getLightMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

    fun darkMutedColor(bitmap: Bitmap): Int {
        return get(bitmap).getDarkMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR))
    }

}
