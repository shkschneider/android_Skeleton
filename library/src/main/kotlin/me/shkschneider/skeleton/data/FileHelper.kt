package me.shkschneider.skeleton.data

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.RawRes
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.AssetsHelper
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.ui.BitmapHelper
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileHelper {

    const val PREFIX_ASSETS = "file:///android_asset/"
    const val PREFIX_RES = "file:///android_res/"
    const val BUFFER_SIZE = DEFAULT_BUFFER_SIZE

    fun join(dirname: String, basename: String): String {
        val file = File(dirname, basename)
        try {
            return file.canonicalPath
        } catch (e: IOException) {
            Logger.wtf(e)
            return file.path
        }
    }

    fun get(path: String): File {
        return File(path)
    }

    fun openFile(file: File): InputStream? {
        try {
            return FileInputStream(file)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun openRaw(@RawRes id: Int): InputStream? {
        try {
            return ApplicationHelper.resources().openRawResource(id)
        } catch (e: Resources.NotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun openAsset(assetName: String): InputStream? {
        return AssetsHelper.open(assetName)
    }

    fun readString(file: File): String? {
        try {
            return StreamHelper.read(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun writeString(file: File, content: String): Boolean {
        try {
            return StreamHelper.write(FileOutputStream(file), content)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            return false
        }
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

    fun list(file: File): List<String>? {
        if (! file.isDirectory) {
            Logger.debug("File was not a directory")
            return null
        }
        return file.listFiles()?.let { files ->
            files.mapTo(ArrayList<String>()) { file -> file.absolutePath }
        }
    }

    fun remove(file: File): Boolean {
        return file.delete()
    }

}
