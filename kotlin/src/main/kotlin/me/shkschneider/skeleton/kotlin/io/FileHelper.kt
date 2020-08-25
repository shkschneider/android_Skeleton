package me.shkschneider.skeleton.kotlin.io

import me.shkschneider.skeleton.kotlin.jvm.tryOr
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileHelper {

    const val BUFFER_SIZE = DEFAULT_BUFFER_SIZE

    fun join(dirname: String, basename: String): String =
        with(File(dirname, basename)) {
            try {
                canonicalPath
            } catch (e: IOException) {
                Logger.wtf(e)
                path
            }
        }

    fun get(path: String): File =
        File(path)

    fun openFile(file: File): InputStream? =
        tryOrNull {
            FileInputStream(file)
        }

    fun readString(file: File): String? =
        tryOrNull {
            StreamHelper.read(FileInputStream(file))
        }

    fun writeString(file: File, content: String): Boolean =
        tryOr(false) {
            StreamHelper.write(FileOutputStream(file), content)
        } == true

}
