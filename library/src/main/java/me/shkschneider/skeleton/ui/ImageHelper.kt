package me.shkschneider.skeleton.ui

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.RelativeLayout
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Metrics

object ImageHelper {

    object Bitmap {

        fun fromResource(@DrawableRes id: Int): android.graphics.Bitmap? {
            return BitmapFactory.decodeResource(ApplicationHelper.resources(), id)
        }

        fun fromDrawable(drawable: android.graphics.drawable.Drawable): android.graphics.Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = android.graphics.Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        fun fromView(view: View): android.graphics.Bitmap? {
            val displayMetrics = Metrics.displayMetrics()
            view.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
            view.buildDrawingCache()
            val bitmap = android.graphics.Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, android.graphics.Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

    }

    object Drawable {

        fun fromResource(@DrawableRes id: Int): android.graphics.drawable.Drawable? {
            return ContextCompat.getDrawable(ContextHelper.applicationContext(), id)
        }

        fun fromBitmap(bitmap: android.graphics.Bitmap) : android.graphics.drawable.Drawable? {
            return BitmapDrawable(ApplicationHelper.resources(), bitmap)
        }

    }

    fun circular(bitmap: android.graphics.Bitmap) : android.graphics.drawable.Drawable {
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplicationHelper.resources(), bitmap)
        roundedBitmapDrawable.isCircular = true
        roundedBitmapDrawable.setAntiAlias(true)
        return roundedBitmapDrawable
    }

}
