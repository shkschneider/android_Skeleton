package me.shkschneider.skeleton.java

import android.support.annotation.IntRange
import android.text.TextUtils
import android.util.Patterns

import java.text.Normalizer
import java.text.NumberFormat

import me.shkschneider.skeleton.helper.LocaleHelper

object StringHelper {

    val ALPHA = CharRange('a', 'z').toString()
    val NUMERIC = CharRange('0', '9').toString()
    val HEX = NUMERIC + ALPHA.substring(0, 6)
    val ALPHA_NUMERIC = ALPHA + NUMERIC

    @Deprecated("TextUtils.isEmpty()")
    fun nullOrEmpty(string: String?): Boolean {
        return TextUtils.isEmpty(string)
    }

    @Deprecated("TextUtils.isDigitsOnly()")
    fun numeric(string: String): Boolean {
        return TextUtils.isDigitsOnly(string)
    }

    fun camelCase(strings: Array<String>): String {
        var camelCase = ""
        strings.asSequence().filterNot { TextUtils.isEmpty(it) }
                .forEach {
                    camelCase += if (TextUtils.isEmpty(camelCase)) {
                        lower(it)
                    } else {
                        capitalize(it)
                    }
                }
        return camelCase
    }

    fun capitalize(string: String): String {
        return upper(string.substring(0, 1)) + lower(string.substring(1))
    }

    fun upper(string: String): String {
        return string.toUpperCase(LocaleHelper.locale())
    }

    fun lower(string: String): String {
        return string.toLowerCase(LocaleHelper.locale())
    }

    fun alpha(string: String): Boolean {
        return chars(lower(string), ALPHA)
    }

    fun alphanumeric(string: String): Boolean {
        return chars(lower(string), ALPHA_NUMERIC)
    }

    fun hexadecimal(string: String): Boolean {
        return chars(lower(string), HEX)
    }

    fun url(string: String): Boolean {
        return Patterns.WEB_URL.matcher(string).matches()
    }

    fun email(string: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

    fun phone(string: String): Boolean {
        return Patterns.PHONE.matcher(string).matches()
    }

    fun count(string: String, s: String): Int {
        return string.length - string.replace(s, "").length
    }

    private fun chars(string: String, chars: String): Boolean {
        return string.toCharArray().any { chars.contains(it.toString()) }
    }

    // <http://stackoverflow.com/a/3758880>
    fun humanReadableSize(@IntRange(from = 0) bytes: Long, binary: Boolean = true): String {
        val unit = if (binary) 1024 else 1000
        if (bytes < unit) {
            return bytes.toString() + " B"
        }
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        return String.format(LocaleHelper.locale(),
                "%.1f %sB",
                bytes / Math.pow(unit.toDouble(), exp.toDouble()), (if (binary) "KMGTPE" else "kMGTPE")[exp - 1] + if (binary) "i" else "")
    }

    fun split(string: String): Array<String> {
        return string.split("(?!^)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    // <https://stackoverflow.com/a/3322174/603270>
    fun withoutAccents(s: String): String {
        var string = s
        string = Normalizer.normalize(string, Normalizer.Form.NFD)
        // string = string.replaceAll("[^\\p{ASCII}]", ""); // ascii
        string = string.replace("\\p{M}".toRegex(), "") // unicode
        return string
    }

    fun split(string: String, delimiter: Char): List<String?> {
        return string.split(delimiter)
    }

    fun lines(string: String, skipEmptyLines: Boolean): Array<String> {
        return if (skipEmptyLines) {
            string.split("[\n\r]+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        } else {
            string.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
    }

    fun ellipsize(string: String, @IntRange(from = 0) maxLength: Int): String {
        return if (string.length > maxLength) {
            string.substring(0, maxLength - 3) + "..."
        } else string
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

    // <http://stackoverflow.com/a/4903603>
    fun repeat(s: String, n: Int): String {
        return String(CharArray(n)).replace("\u0000", s)
    }

}
