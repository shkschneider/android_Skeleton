package me.shkschneider.skeleton.ui

import android.graphics.Paint
import android.os.Build
import androidx.core.widget.TextViewCompat
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView

object TextViewHelper {

    // <https://stackoverflow.com/a/10947374>
    fun underline(textView: TextView, underline: Boolean) {
        val flags = if (underline) (textView.paintFlags or Paint.DEV_KERN_TEXT_FLAG) else (textView.paintFlags and Paint.DEV_KERN_TEXT_FLAG.inv())
        textView.paintFlags = flags
    }

    fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
        val flags = if (strikeThrough) (textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG) else (textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        textView.paintFlags = flags
    }

    fun html(textView: TextView, html: String) {
        if (Build.VERSION.SDK_INT >= 24) {
            textView.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            textView.text = Html.fromHtml(html)
        }
    }

    fun linkify(textView: TextView, urls: Boolean, emails: Boolean, phones: Boolean, addresses: Boolean) {
        var links = 0
        if (urls) links = links or Linkify.WEB_URLS
        if (emails) links = links or Linkify.EMAIL_ADDRESSES
        if (phones) links = links or Linkify.PHONE_NUMBERS
        if (addresses) {
            if (Build.VERSION.SDK_INT >= 28) {
                // TODO I just don't get how to use TextClassifier
            } else {
                @Suppress("DEPRECATION")
                links = links or Linkify.MAP_ADDRESSES
            }
        }
        val spannableString = SpannableString(textView.text)
        Linkify.addLinks(spannableString, links)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    fun linkify(textView: TextView) {
        val spannableString = SpannableString(textView.text)
        Linkify.addLinks(spannableString, Linkify.ALL)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    fun autoSize(textView: TextView, autoSize: Boolean) {
        val autoSizeTextType = if (autoSize) TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM else TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, autoSizeTextType)
    }

}
