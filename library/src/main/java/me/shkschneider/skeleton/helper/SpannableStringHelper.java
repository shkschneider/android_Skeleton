package me.shkschneider.skeleton.helper;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

// <https://gist.github.com/Trikke/90efd4432fc09aaadf3e>
public class SpannableStringHelper {

    private final Spannable mSpannable;

    public SpannableStringHelper(@NonNull final String string) {
        mSpannable = new SpannableString(string);
    }

    public SpannableStringHelper strikethrough(final int start, final int length) {
        final StrikethroughSpan span = new StrikethroughSpan();
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper underline(final int start, final int length) {
        final UnderlineSpan span = new UnderlineSpan();
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper boldify(final int start, final int length) {
        final StyleSpan span = new StyleSpan(android.graphics.Typeface.BOLD);
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper italize(final int start, final int length) {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper colorize(final int start, final int length, final int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper mark(final int start, final int length, final int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringHelper proportionate(final int start, final int length, final float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);
        mSpannable.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Spannable apply() {
        return mSpannable;
    }

}
