package me.shkschneider.skeleton.ui;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import me.shkschneider.skeleton.network.MyImageGetter;

public class TextViewHelper {

    // <https://stackoverflow.com/a/10947374>
    public static void underline(@NonNull final TextView textView, final boolean underline) {
        int flags = (underline ? textView.getPaintFlags() | Paint.DEV_KERN_TEXT_FLAG : textView.getPaintFlags() & ~Paint.DEV_KERN_TEXT_FLAG);
        textView.setPaintFlags(flags);
    }

    public static void strikeThrough(@NonNull final TextView textView, final boolean strikeThrough) {
        int flags = (strikeThrough ? textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        textView.setPaintFlags(flags);
    }

    @SuppressWarnings("deprecation")
    public static Spanned html(@NonNull final String html) {
        return Html.fromHtml(html, null, null);
    }

    @SuppressWarnings("deprecation")
    public static Spanned htmlWithImages(@NonNull final TextView textView, @NonNull final String html) {
        return Html.fromHtml(html, new MyImageGetter(textView), null);
    }

    public static void linkify(@NonNull final TextView textView, final boolean urls, final boolean emails, final boolean phones, final boolean addresses) {
        int links = 0;
        if (urls) links |= Linkify.WEB_URLS;
        if (emails) links |= Linkify.EMAIL_ADDRESSES;
        if (phones) links |= Linkify.PHONE_NUMBERS;
        if (addresses) links |= Linkify.MAP_ADDRESSES;
        final SpannableString spannableString = new SpannableString(textView.getText());
        Linkify.addLinks(spannableString, links);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void linkify(@NonNull final TextView textView) {
        final SpannableString spannableString = new SpannableString(textView.getText());
        Linkify.addLinks(spannableString, Linkify.ALL);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void autoSize(@NonNull final TextView textView, final boolean autoSize) {
        final int autoSizeTextType = (autoSize ? TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM : TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, autoSizeTextType);
    }

}
