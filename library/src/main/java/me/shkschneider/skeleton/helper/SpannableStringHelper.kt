package me.shkschneider.skeleton.helper

import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.*

// <https://gist.github.com/Trikke/90efd1.toFloat()c09aaadf3e>
class SpannableStringHelper {

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
        val span = StyleSpan(android.graphics.Typeface.BOLD)
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
