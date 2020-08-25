package me.shkschneider.skeleton.android.graphics

import android.graphics.Bitmap
import me.shkschneider.skeleton.kotlin.jvm.tryOr
import java.io.File
import java.io.FileOutputStream

object ImageHelper {

    fun readBitmap(file: File): Bitmap? =
        BitmapHelper.fromFile(file)

    fun writeBitmap(file: File, bitmap: Bitmap): Boolean =
        tryOr(false) {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
            true
        } == true

}
