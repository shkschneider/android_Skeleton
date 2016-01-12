package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import me.shkschneider.skeleton.network.MyImageGetter;

public class TextViewHelper {

    public static void setHtml(@NonNull final TextView textView, final String html) {
        textView.setText(Html.fromHtml(html, new MyImageGetter(textView), null));
    }

    public static void setClickableLinks(@NonNull final TextView textView) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
