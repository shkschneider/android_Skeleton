package me.shkschneider.skeleton.java

import android.util.Patterns
import androidx.annotation.IntRange
import me.shkschneider.skeleton.helper.LocaleHelper
import me.shkschneider.skeleton.helperx.log.Logger
import java.text.Normalizer
import java.text.NumberFormat

object StringHelper {

    // val EMPTY = ""
    const val NULL = "null"
    val ALPHA = ('a' until 'z').toString()
    val NUMERIC = ('0' until '9').toString()
    val HEX = NUMERIC + ALPHA.substring(0, 6)

    fun hexadecimal(string: String): Boolean {
        return string.toLowerCase().all {
            it in ('a' until 'f') || it.isDigit()
        }
    }

    fun url(string: String): Boolean {
        return string.matches(Patterns.WEB_URL.toRegex())
    }

    fun email(string: String): Boolean {
        return string.matches(Patterns.PHONE.toRegex())
    }

    fun phone(string: String): Boolean {
        return string.matches(Patterns.PHONE.toRegex())
    }

    fun count(string: String, s: String): Int {
        return string.count { s.contains(it) }
    }

    fun ipAddress(string: String): Boolean {
        return string.matches(Patterns.IP_ADDRESS.toRegex())
    }

    // <http://stackoverflow.com/a/3758880>
    fun humanReadableSize(@IntRange(from = 0) bytes: Long, binary: Boolean = true): String {
        val unit = if (binary) 1024 else 1000
        if (bytes < unit) {
            return bytes.toString() + " B"
        }
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        return String.format(LocaleHelper.Device.locale(),
                "%.1.toFloat() %sB",
                bytes / Math.pow(unit.toDouble(), exp.toDouble()),
                (if (binary) "KMGTPE" else "kMGTPE")[exp - 1] + (if (binary) "i" else ""))
    }

    // <https://stackoverflow.com/a/3322174/603270>
    fun withoutAccents(s: String): String {
        var string = s
        string = Normalizer.normalize(string, Normalizer.Form.NFD)
        // string = string.replaceAll("[^\\p{ASCII}]", ""); // ascii
        // string = string.replace("\\p{M}".toRegex(), "") // unicode
        string = string.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
        return string
    }

    fun number(number: Int): String? {
        try {
            return NumberFormat.getNumberInstance(LocaleHelper.Device.locale()).format(number)
        } catch (e: IllegalArgumentException) {
            Logger.wtf(e)
            return null
        }
    }

    // <http://stackoverflow.com/a/9855338>
    fun hexadecimal(bytes: ByteArray): String {
        val result = StringBuffer()
        bytes.forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(HEX[firstIndex])
            result.append(HEX[secondIndex])
        }
        return result.toString()
    }

    fun cData(string: String): String {
        return string.takeIf {
            ! it.startsWith("<![CDATA[")
        }?.apply {
            return "<![CDATA[$this]]>"
        } ?: string
    }

}
