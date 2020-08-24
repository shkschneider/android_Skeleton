package me.shkschneider.skeleton.data

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.RawRes
import me.shkschneider.skeleton.extensions.BitmapHelper
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.AssetsHelper
import me.shkschneider.skeleton.helperx.log.Logger
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream

object FileHelper {

    const val PREFIX_ASSETS = "file:///android_asset/"
    const val PREFIX_RES = "file:///android_res/"

    fun openRaw(@RawRes id: Int): InputStream? {
        try {
            return ApplicationHelper.resources.openRawResource(id)
        } catch (e: Resources.NotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun openAsset(assetName: String): InputStream? {
        return AssetsHelper.open(assetName)
    }

    fun readBitmap(file: File): Bitmap? {
        return BitmapHelper.fromFile(file)
    }

    fun writeBitmap(file: File, bitmap: Bitmap): Boolean {
        try {
            val fileOutputStream = FileOutputStream(file)
            return bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return false
        }
    }

}
