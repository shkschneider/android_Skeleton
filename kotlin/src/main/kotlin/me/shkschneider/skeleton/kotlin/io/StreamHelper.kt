package me.shkschneider.skeleton.kotlin.io

import me.shkschneider.skeleton.kotlin.jvm.tryOr
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner

object StreamHelper {

    fun read(inputStream: InputStream): String? =
        tryOrNull {
            // TRICK: The stream gets tokenized, \A meaning the beginning, \\A means the second beginning... so its end.
            Scanner(inputStream).useDelimiter("\\A").next()
        }

    fun write(outputStream: OutputStream, content: String): Boolean =
        tryOr(false) {
            outputStream.write(content.toByteArray())
            outputStream.close()
            true
        } == true

}
