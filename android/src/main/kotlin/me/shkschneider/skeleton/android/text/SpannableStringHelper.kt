package me.shkschneider.skeleton.android.text

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import me.shkschneider.skeleton.android.log.Logger

// <https://gist.github.com/Trikke/90efd4432fc09aaadf3e>
open class SpannableStringHelper {

    private var spannableString: SpannableString

    constructor(spannableString: SpannableString) {
        this.spannableString = spannableString
    }

    constructor(string: String) : this(SpannableString(string))

    constructor(spanned: Spanned) : this(SpannableString(spanned))

    fun strikethrough(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StrikethroughSpan()
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun underline(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = UnderlineSpan()
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun boldify(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StyleSpan(Typeface.BOLD)
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun italize(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StyleSpan(Typeface.ITALIC)
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun colorize(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, @ColorInt color: Int): SpannableStringHelper {
        val span = ForegroundColorSpan(color)
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun mark(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, @ColorInt color: Int): SpannableStringHelper {
        val span = BackgroundColorSpan(color)
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun proportionate(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, proportion: Float): SpannableStringHelper {
        val span = RelativeSizeSpan(proportion)
        try {
            spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Logger.wtf(e)
        }
        return this
    }

    fun apply(): Spannable {
        return spannableString
    }

}
