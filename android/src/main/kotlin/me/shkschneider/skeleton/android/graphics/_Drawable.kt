package me.shkschneider.skeleton.android.graphics

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.ContextProvider

object DrawableHelper {

    fun fromResource(@DrawableRes id: Int): Drawable =
            ContextCompat.getDrawable(ContextProvider.applicationContext(), id) as Drawable

    fun fromBitmap(bitmap: Bitmap): Drawable =
            BitmapDrawable(ApplicationHelper.resources, bitmap)

    fun circular(bitmap: Bitmap): Drawable? =
            bitmap.circular()?.let { fromBitmap(it) }

    fun toBase64(drawable: Drawable): String =
            BitmapHelper.fromDrawable(drawable).toBase64()

}
