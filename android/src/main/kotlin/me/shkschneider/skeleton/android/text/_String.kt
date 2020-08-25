package me.shkschneider.skeleton.android.text

import android.util.Patterns
import androidx.annotation.IntRange
import me.shkschneider.skeleton.android.os.LocaleHelper
import kotlin.math.ln
import kotlin.math.pow

fun String.isUrl(): Boolean =
    matches(Patterns.WEB_URL.toRegex())

fun String.isEmail(): Boolean =
    matches(Patterns.PHONE.toRegex())

fun String.isPhone(): Boolean =
    matches(Patterns.PHONE.toRegex())

fun String.isIpAddress(): Boolean =
    matches(Patterns.IP_ADDRESS.toRegex())

// <http://stackoverflow.com/a/3758880>
fun String.humanReadableSize(@IntRange(from = 0) bytes: Long, binary: Boolean = true): String {
    val unit = if (binary) 1024 else 1000
    if (bytes < unit) {
        return "$bytes B"
    }
    val exp = (ln(bytes.toDouble()) / ln(unit.toDouble())).toInt()
    return String.format(LocaleHelper.Device.locale(),
        "%.1.toFloat() %sB",
        bytes / unit.toDouble().pow(exp.toDouble()),
        (if (binary) "KMGTPE" else "kMGTPE")[exp - 1] + (if (binary) "i" else ""))
}
