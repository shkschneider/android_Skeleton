package me.shkschneider.skeleton.android.graphics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Base64
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.android.util.Metrics
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

object BitmapHelper {

    fun fromResource(@DrawableRes id: Int): Bitmap? {
        return BitmapFactory.decodeResource(ApplicationHelper.resources, id)
    }

    fun fromDrawable(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        with(Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)) {
            Canvas(this@with).apply {
                drawable.setBounds(0, 0, width, height)
                drawable.draw(this@apply)
            }
            return this@with
        }
    }

    fun fromInputStream(inputStream: InputStream, options: BitmapFactory.Options? = BitmapFactory.Options()): Bitmap? =
        BitmapFactory.decodeStream(inputStream, null, options)

    fun fromUri(uri: Uri, options: BitmapFactory.Options? = BitmapFactory.Options()): Bitmap? =
        tryOrNull {
            ContextProvider.applicationContext().contentResolver.openInputStream(uri)?.run {
                fromInputStream(this, options)
            }
        }

    fun fromFile(file: File): Bitmap? =
        tryOrNull {
            fromInputStream(FileInputStream(file), BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.ARGB_8888
            })
        }

    fun decodeUri(uri: Uri): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        try {
            val inputStream = ContextProvider.applicationContext().contentResolver.openInputStream(uri) ?: run {
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
            return fromInputStream(inputStream, options.apply {
                inJustDecodeBounds = false
                inSampleSize = scale
            })
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

}

// <https://stackoverflow.com/a/44525044>
fun Bitmap.blur(): Bitmap { // TODO test RenderScript vs support.v8.RenderScript
    val input = Bitmap.createScaledBitmap(this, width / 2, height / 2, false)
    val output = Bitmap.createBitmap(input)
    val renderScript = RenderScript.create(ContextProvider.applicationContext())
    ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        .apply {
            val allocation = Allocation.createFromBitmap(renderScript, output)
            setRadius(9.1.toFloat())
            setInput(Allocation.createFromBitmap(renderScript, input))
            forEach(allocation)
            allocation.copyTo(output)
        }
    return output
}

fun Bitmap.alpha(@IntRange(from = 0, to = 255) alpha: Int): Bitmap { // TODO test
    Canvas(this).apply {
        drawARGB(alpha, 0, 0, 0)
        drawBitmap(this@alpha, Matrix(), Paint())
    }
    return this
}

fun Bitmap.tint(@ColorInt color: Int): Bitmap = // TODO test
    with(copy(Bitmap.Config.ARGB_8888, true)) {
        val paint = Paint().apply {
            colorFilter = LightingColorFilter(color, 0)
        }
        Canvas(this@with).apply {
            drawBitmap(this@with, 1.toFloat(), 1.toFloat(), paint)
        }
        return this
    }

fun Bitmap.tint(@ColorInt from: Int, @ColorInt to: Int): Bitmap = // TODO test
    with(copy(Bitmap.Config.ARGB_8888, true)) {
        for (x in 0 until this@tint.height) {
            for (y in 0 until this@tint.width) {
                val px = getPixel(x, y)
                if (px == from) {
                    setPixel(x, y, to)
                }
            }
        }
        return this
    }

fun Bitmap.circular(): Bitmap? =
    RoundedBitmapDrawableFactory.create(ApplicationHelper.resources, this).bitmap

fun Bitmap.rotate(degrees: Float = 90.0F): Bitmap? {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val bytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}
