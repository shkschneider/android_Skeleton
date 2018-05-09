package me.shkschneider.skeleton.network

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView

import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.Logger

// android.text.Html.fromHtml(String, MyImageGetter, null)
// <http://stackoverflow.com/a/25530488>
class MyImageGetter(
        private val textView: TextView
) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val bitmapDrawablePlaceholder = BitmapDrawablePlaceholder()
        Proxy.imageLoader.get(source, object: ImageLoader.ImageListener {
            override fun onResponse(response: ImageLoader.ImageContainer, isImmediate: Boolean) {
                val bitmap = response.bitmap ?: run {
                    Logger.warning("Bitmap was NULL")
                    return
                }
                val bitmapDrawable = BitmapDrawable(ApplicationHelper.resources(), bitmap)
                val width = bitmapDrawable.intrinsicWidth
                val height = bitmapDrawable.intrinsicHeight
                bitmapDrawable.setBounds(0, 0, width, height)
                bitmapDrawablePlaceholder.setDrawable(bitmapDrawable)
                bitmapDrawablePlaceholder.setBounds(0, 0, width, height)
                textView.text = textView.text // HACK: invalidate()
            }
            override fun onErrorResponse(error: VolleyError) {
                if (! error.cause?.message.isNullOrBlank()) {
                    Logger.error(error.cause?.message.orEmpty())
                }
            }
        })
        return bitmapDrawablePlaceholder
    }

    @Suppress("DEPRECATION")
    private class BitmapDrawablePlaceholder : BitmapDrawable() {

        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        fun setDrawable(drawable: Drawable) {
            this.drawable = drawable
        }

    }

}
