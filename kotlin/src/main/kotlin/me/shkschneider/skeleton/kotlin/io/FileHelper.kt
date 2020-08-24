package me.shkschneider.skeleton.kotlin.io

import me.shkschneider.skeleton.kotlin.io.StreamHelper
import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
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
        try {
            FileInputStream(file)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            null
        }

    fun readString(file: File): String? =
        try {
            StreamHelper.read(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            null
        }

    fun writeString(file: File, content: String): Boolean =
        try {
            StreamHelper.write(FileOutputStream(file), content)
        } catch (e: FileNotFoundException) {
            Logger.wtf(e)
            false
        }

}
