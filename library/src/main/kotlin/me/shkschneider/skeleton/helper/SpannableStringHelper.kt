package me.shkschneider.skeleton.helper

import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

// <https://gist.github.com/Trikke/90efd4432fc09aaadf3e>
class SpannableStringHelper {

    private var _spannableString: SpannableString

    constructor(spannableString: SpannableString) {
        _spannableString = spannableString
    }

    constructor(string: String) : this(SpannableString(string))

    constructor(spanned: Spanned) : this(SpannableString(spanned))

    fun strikethrough(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StrikethroughSpan()
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun underline(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = UnderlineSpan()
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun boldify(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StyleSpan(android.graphics.Typeface.BOLD)
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun italize(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int): SpannableStringHelper {
        val span = StyleSpan(Typeface.ITALIC)
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun colorize(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, @ColorInt color: Int): SpannableStringHelper {
        val span = ForegroundColorSpan(color)
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun mark(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, @ColorInt color: Int): SpannableStringHelper {
        val span = BackgroundColorSpan(color)
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun proportionate(@IntRange(from = 0) start: Int, @IntRange(from = 0) length: Int, proportion: Float): SpannableStringHelper {
        val span = RelativeSizeSpan(proportion)
        _spannableString.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun apply(): Spannable {
        return _spannableString
    }

}
