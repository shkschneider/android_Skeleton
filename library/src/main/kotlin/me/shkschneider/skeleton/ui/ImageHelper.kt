package me.shkschneider.skeleton.ui

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Metrics

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
            if (Build.VERSION.SDK_INT < 28) {
                /**
                 * This method was deprecated in API level 28. The view drawing cache was largely
                 * made obsolete with the introduction of hardware-accelerated rendering in API 11.
                 */
                @Suppress("DEPRECATION")
                view.buildDrawingCache()
            }
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
