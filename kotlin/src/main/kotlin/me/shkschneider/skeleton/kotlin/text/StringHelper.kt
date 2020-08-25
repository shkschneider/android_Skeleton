package me.shkschneider.skeleton.kotlin.text

import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.text.Normalizer
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

object StringHelper {

    const val EMPTY = ""
    const val NULL = "null"
    val ALPHA = ('a'..'z').toString()
    val NUMERIC = ('0'..'9').toString()
    val HEX = NUMERIC + ALPHA.substring(0, 6)

    fun count(string: String, s: String): Int =
        string.count { s.contains(it) }

    // <http://stackoverflow.com/a/3758880>
    fun humanReadableSize(bytes: Long, binary: Boolean = true): String {
        val unit = if (binary) 1024 else 1000
        if (bytes < unit) {
            return "$bytes B"
        }
        val exp = (ln(bytes.toDouble()) / ln(unit.toDouble())).toInt()
        return "%.1.toFloat() %sB".format(
            bytes / unit.toDouble().pow(exp.toDouble()),
            (if (binary) "KMGTPE" else "kMGTPE")[exp - 1] + (if (binary) "i" else "")
        )
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

    fun number(number: Int): String? =
        tryOrNull {
            NumberFormat.getNumberInstance().format(number)
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

    fun isHexadecimal(string: String): Boolean =
        string.toLowerCase(Locale.getDefault()).all {
            it in ('a' until 'f') || it.isDigit()
        }

    fun cData(string: String): String =
        string.takeIf {
            !it.startsWith("<![CDATA[")
        }?.apply {
            return "<![CDATA[$this]]>"
        } ?: string

}
