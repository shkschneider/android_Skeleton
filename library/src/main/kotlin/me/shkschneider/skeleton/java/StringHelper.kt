package me.shkschneider.skeleton.java

import android.support.annotation.IntRange
import android.text.TextUtils
import android.util.Patterns
import me.shkschneider.skeleton.helper.LocaleHelper
import java.text.Normalizer
import java.text.NumberFormat
import java.util.regex.Pattern

object StringHelper {

    val ALPHA = String(CharRange('a', 'z').toList().toCharArray())
    val NUMERIC = String(CharRange('0', '9').toList().toCharArray())
    val HEX = NUMERIC + ALPHA.substring(0, 6)
    val ALPHA_NUMERIC = ALPHA + NUMERIC

    fun alpha(string: String): Boolean {
        return string.matches(Pattern.compile(ALPHA).toRegex())
    }

    fun alphaNumeric(string: String): Boolean {
        return string.matches(Pattern.compile(ALPHA_NUMERIC).toRegex())
    }

    @Deprecated("TextUtils.isDigitsOnly()")
    fun numeric(string: String): Boolean {
        return TextUtils.isDigitsOnly(string)
    }

    fun hexadecimal(string: String): Boolean {
        return string.matches(HEX.toRegex())
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

    // <http://stackoverflow.com/a/3758880>
    fun humanReadableSize(@IntRange(from = 0) bytes: Long, binary: Boolean = true): String {
        val unit = if (binary) 1024 else 1000
        if (bytes < unit) {
            return bytes.toString() + " B"
        }
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        return String.format(LocaleHelper.locale(),
                "%.1.toFloat() %sB",
                bytes / Math.pow(unit.toDouble(), exp.toDouble()), (if (binary) "KMGTPE" else "kMGTPE")[exp - 1] + if (binary) "i" else "")
    }

    // <https://stackoverflow.com/a/3322174/603270>
    fun withoutAccents(s: String): String {
        var string = s
        string = Normalizer.normalize(string, Normalizer.Form.NFD)
        // string = string.replaceAll("[^\\p{ASCII}]", ""); // ascii
        string = string.replace("\\p{M}".toRegex(), "") // unicode
        return string
    }

    fun number(number: Int): String {
        return NumberFormat.getNumberInstance(LocaleHelper.locale()).format(number)
    }

    // <http://stackoverflow.com/a/9855338>
    fun hexadecimal(bytes: ByteArray): String? {
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

}
