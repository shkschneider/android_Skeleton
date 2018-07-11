package me.shkschneider.skeleton.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper

object DrawableHelper {

    fun fromResource(@DrawableRes id: Int): Drawable {
        return ContextCompat.getDrawable(ContextHelper.applicationContext(), id) as Drawable
    }

    fun fromBitmap(bitmap: Bitmap): Drawable {
        return BitmapDrawable(ApplicationHelper.resources(), bitmap)
    }

    fun circular(bitmap: Bitmap): Drawable {
        @Suppress("DEPRECATION")
        return BitmapDrawable(ApplicationHelper.resources(), BitmapHelper.circular(bitmap))
    }

    fun toBase64(drawable: Drawable): String {
        return BitmapHelper.toBase64(BitmapHelper.fromDrawable(drawable))
    }

}
