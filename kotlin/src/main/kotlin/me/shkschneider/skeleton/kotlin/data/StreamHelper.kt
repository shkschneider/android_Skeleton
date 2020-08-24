package me.shkschneider.skeleton.kotlin.data

import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner

object StreamHelper {

    fun read(inputStream: InputStream): String? =
        try {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            Scanner(inputStream).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            Logger.wtf(e)
            null
        }

    fun write(outputStream: OutputStream, content: String): Boolean =
        try {
            outputStream.write(content.toByteArray())
            outputStream.close()
            true
        } catch (e: IOException) {
            Logger.wtf(e)
            false
        }

}