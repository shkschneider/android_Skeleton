package me.shkschneider.skeleton.data

import android.content.res.Resources
import android.graphics.Bitmap
import android.support.annotation.RawRes
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.AssetsHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.ui.BitmapHelper
import java.io.*
import java.util.*

object FileHelper {

    val PREFIX_ASSETS = "file:///android_asset/"
    val PREFIX_RES = "file:///android_res/"

    fun join(dirname: String, basename: String): String {
        val file = File(dirname, basename)
        try {
            return file.canonicalPath
        } catch (e: IOException) {
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
            LogHelper.wtf(e)
            return null
        }
    }

    fun openRaw(@RawRes id: Int): InputStream? {
        try {
            return ApplicationHelper.resources().openRawResource(id)
        } catch (e: Resources.NotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun openAsset(assetName: String): InputStream? {
        return AssetsHelper.open(assetName)
    }

    fun readString(inputStream: InputStream): String? {
        try {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            return Scanner(inputStream).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    fun readString(file: File): String? {
        try {
            return readString(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun writeString(outputStream: OutputStream, content: String): Boolean {
        try {
            outputStream.write(content.toByteArray())
            outputStream.close()
            return true
        } catch (e: IOException) {
            LogHelper.wtf(e)
            return false
        }
    }

    fun writeString(file: File, content: String): Boolean {
        try {
            return writeString(FileOutputStream(file), content)
        } catch (e: FileNotFoundException) {
            LogHelper.wtf(e)
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
            LogHelper.wtf(e)
            return false
        }
    }

    fun list(file: File): List<String>? {
        if (! file.isDirectory) {
            LogHelper.debug("File was not a directory")
            return null
        }
        return file.listFiles()?.let {
            it.mapTo(ArrayList<String>()) { it.absolutePath }
        }
    }

    fun remove(file: File): Boolean {
        return file.delete()
    }

}
