package me.shkschneider.skeleton.android.graphics

import android.graphics.Bitmap
import me.shkschneider.skeleton.android.graphics.BitmapHelper
import me.shkschneider.skeleton.android.log.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

object ImageHelper {

    fun readBitmap(file: File): Bitmap? =
        BitmapHelper.fromFile(file)

    fun writeBitmap(file: File, bitmap: Bitmap): Boolean =
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            false
        }

}
