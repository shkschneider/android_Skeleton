package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import me.shkschneider.skeleton.network.MyImageGetter;

public class TextViewHelper {

    @SuppressWarnings("deprecation")
    public static Spanned html(@NonNull final String html) {
        return Html.fromHtml(html, null, null);
    }

    @SuppressWarnings("deprecation")
    public static Spanned htmlWithImages(@NonNull final Context context, @NonNull final TextView textView, @NonNull final String html) {
        return Html.fromHtml(html, new MyImageGetter(context, textView), null);
    }

    public static void linkify(final TextView textView, final boolean urls, final boolean emails, final boolean phones, final boolean addresses) {
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

    public static void linkify(final TextView textView) {
        final SpannableString spannableString = new SpannableString(textView.getText());
        Linkify.addLinks(spannableString, Linkify.ALL);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
