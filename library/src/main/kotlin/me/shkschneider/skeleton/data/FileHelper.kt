package me.shkschneider.skeleton.data

import android.content.res.Resources
import android.graphics.Bitmap
import android.support.annotation.RawRes
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.AssetsHelper
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.ui.BitmapHelper
import java.io.*
import java.util.*

object FileHelper {

    val PREFIX_ASSETS = "file:///android_asset/"
    val PREFIX_RES = "file:///android_res/"

    fun join(dirname: String, basename: String): String {
        val file = File(dirname, basename)
        return try {
            file.canonicalPath
        } catch (e: IOException) {
            file.path
        }
    }

    fun get(path: String): File {
        return File(path)
    }

    fun openFile(file: File): InputStream? {
        return try {
            FileInputStream(file)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            null
        }
    }

    fun openRaw(@RawRes id: Int): InputStream? {
        return try {
            ApplicationHelper.resources().openRawResource(id)
        } catch (e: Resources.NotFoundException) {
            Logger.wtf(e)
            null
        }
    }

    fun openAsset(assetName: String): InputStream? {
        return AssetsHelper.open(assetName)
    }

    fun readString(inputStream: InputStream): String? {
        return try {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            Scanner(inputStream).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun readString(file: File): String? {
        return try {
            readString(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            null
        }
    }

    fun writeString(outputStream: OutputStream, content: String): Boolean {
        return try {
            outputStream.write(content.toByteArray())
            outputStream.close()
            true
        } catch (e: IOException) {
            Logger.wtf(e)
            false
        }
    }

    fun writeString(file: File, content: String): Boolean {
        return try {
            writeString(FileOutputStream(file), content)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            false
        }
    }

    fun readBitmap(file: File): Bitmap? {
        return BitmapHelper.fromFile(file)
    }

    fun writeBitmap(file: File, bitmap: Bitmap): Boolean {
        return try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            false
        }
    }

    fun list(file: File): List<String>? {
        if (! file.isDirectory) {
            Logger.debug("File was not a directory")
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
