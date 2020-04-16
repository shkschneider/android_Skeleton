package me.shkschneider.skeleton.extensions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper

object DrawableHelper {

    fun fromResource(@DrawableRes id: Int): Drawable =
            ContextCompat.getDrawable(ContextHelper.applicationContext(), id) as Drawable

    fun fromBitmap(bitmap: Bitmap): Drawable =
            BitmapDrawable(ApplicationHelper.resources, bitmap)

    fun circular(bitmap: Bitmap): Drawable? =
            bitmap.circular()?.let { fromBitmap(it) }

    fun toBase64(drawable: Drawable): String =
            BitmapHelper.fromDrawable(drawable).toBase64()

}
