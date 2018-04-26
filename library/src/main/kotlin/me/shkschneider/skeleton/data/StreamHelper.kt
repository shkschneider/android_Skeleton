package me.shkschneider.skeleton.data

import me.shkschneider.skeleton.helper.Logger
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object StreamHelper {

    fun read(inputStream: InputStream): String? {
        return try {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            Scanner(inputStream).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun write(outputStream: OutputStream, content: String): Boolean {
        return try {
            outputStream.write(content.toByteArray())
            outputStream.close()
            true
        } catch (e: IOException) {
            Logger.wtf(e)
            false
        }
    }

}
