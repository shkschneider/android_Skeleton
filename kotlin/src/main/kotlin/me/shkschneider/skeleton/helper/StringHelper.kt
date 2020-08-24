package me.shkschneider.skeleton.helper

import me.shkschneider.skeleton.log.Logger
import java.text.Normalizer
import java.text.NumberFormat
import kotlin.math.ln
import kotlin.math.pow

object StringHelper {

    const val EMPTY = ""
    const val NULL = "null"
    val ALPHA = ('a'..'z').toString()
    val NUMERIC = ('0'..'9').toString()

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
        try {
            NumberFormat.getNumberInstance().format(number)
        } catch (e: IllegalArgumentException) {
            Logger.wtf(e)
            null
        }

    fun cData(string: String): String =
        string.takeIf {
            !it.startsWith("<![CDATA[")
        }?.apply {
            return "<![CDATA[$this]]>"
        } ?: string

}
