package me.shkschneider.skeleton.ui

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.annotation.RequiresApi
import android.util.Base64
import android.view.View
import android.widget.RelativeLayout
import me.shkschneider.skeleton.helper.*
import java.io.*

object BitmapHelper {

    fun fromResource(@DrawableRes id: Int): Bitmap {
        return BitmapFactory.decodeResource(ApplicationHelper.resources(), id)
    }

    fun fromDrawable(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    // <https://stackoverflow.com/a/44525044>
    @RequiresApi(AndroidHelper.API_17)
    fun blur(original: Bitmap): Bitmap {
        val input = Bitmap.createScaledBitmap(original, original.width / 2, original.height / 2, false)
        val output = Bitmap.createBitmap(input)
        val renderScript = RenderScript.create(ContextHelper.applicationContext())
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        val allocation = Allocation.createFromBitmap(renderScript, output)
        scriptIntrinsicBlur.setRadius(9.1.toFloat())
        scriptIntrinsicBlur.setInput(Allocation.createFromBitmap(renderScript, input))
        scriptIntrinsicBlur.forEach(allocation)
        allocation.copyTo(output)
        return output
    }

    fun alpha(bitmap: Bitmap, @IntRange(from = 0, to = 255) alpha: Int): Bitmap {
        val canvas = Canvas(bitmap)
        canvas.drawARGB(alpha, 0, 0, 0)
        canvas.drawBitmap(bitmap, Matrix(), Paint())
        return bitmap
    }

    fun tint(bitmap: Bitmap, @ColorInt color: Int): Bitmap {
        val tintedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()
        val colorFilter = LightingColorFilter(color, 0)
        paint.colorFilter = colorFilter
        val canvas = Canvas(tintedBitmap)
        canvas.drawBitmap(tintedBitmap, 1.toFloat(), 1.toFloat(), paint)
        return tintedBitmap
    }

    fun tint(bitmap: Bitmap, @ColorInt from: Int, @ColorInt to: Int): Bitmap {
        val tintedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        for (x in 0 until bitmap.height) {
            for (y in 0 until bitmap.width) {
                val px = tintedBitmap.getPixel(x, y)
                if (px == from) {
                    tintedBitmap.setPixel(x, y, to)
                }
            }
        }
        return tintedBitmap
    }

    @Deprecated("android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory")
    fun circular(bitmap: Bitmap): Bitmap {
        val circularBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = bitmapShader
        val canvas = Canvas(circularBitmap)
        canvas.drawCircle((circularBitmap.width / 2).toFloat(), (circularBitmap.height / 2).toFloat(), (circularBitmap.width / 2).toFloat(), paint)
        return circularBitmap
    }

    fun fromView(view: View): Bitmap? {
        val displayMetrics = Metrics.displayMetrics()
        view.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun fromInputStream(inputStream: InputStream, options: BitmapFactory.Options?): Bitmap? {
        val _options = options ?: BitmapFactory.Options()
        return BitmapFactory.decodeStream(inputStream, null, _options)
    }

    fun fromUri(uri: Uri, options: BitmapFactory.Options?): Bitmap? {
        val _options = options ?: BitmapFactory.Options()
        try {
            val inputStream = ContextHelper.applicationContext().contentResolver.openInputStream(uri) ?: return null
            return BitmapHelper.fromInputStream(inputStream, _options)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }

    }

    fun fromFile(file: File): Bitmap? {
        try {
            return BitmapHelper.fromInputStream(FileInputStream(file), object: BitmapFactory.Options() {
                init {
                    inPreferredConfig = Bitmap.Config.ARGB_8888
                }
            })
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }

    }

    fun compress(bitmap: Bitmap, factor: Int): Bitmap {
        if (factor <= 0) {
            return bitmap
        }
        if ((factor and factor - 1) != 0) {
            Logger.warning("Factor should be a power of 2")
        }
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        options.inSampleSize = factor
        if (AndroidHelper.api() < AndroidHelper.API_24) {
            @Suppress("DEPRECATION")
            options.inPreferQualityOverSpeed = true
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }

    fun decodeUri(uri: Uri): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            val inputStream = ContextHelper.applicationContext().contentResolver.openInputStream(uri)
            inputStream ?: run {
                Logger.warning("InputStream was NULL")
                return null
            }
            // Tries to optimize the process
            var width = options.outWidth
            var height = options.outHeight
            var scale = 1
            val downSample = Metrics.density().toInt()
            if (downSample <= 0) {
                Logger.warning("Downsample was invalid")
                return null
            }
            while (true) {
                if (width / 2 < downSample || height / 2 < downSample) {
                    break
                }
                width /= 2
                height /= 2
                scale *= 2
            }
            options.inJustDecodeBounds = false
            options.inSampleSize = scale
            return BitmapHelper.fromInputStream(inputStream, options)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun toBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

}
