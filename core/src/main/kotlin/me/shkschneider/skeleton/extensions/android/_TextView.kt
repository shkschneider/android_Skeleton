package me.shkschneider.skeleton.extensions.android

import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.core.widget.TextViewCompat

// <https://stackoverflow.com/a/46286470/603270>
fun TextView.autoSize() = doOnPreDraw {
    val numberOfCompletelyVisibleLines = (measuredHeight - paddingTop - paddingBottom) / lineHeight
    maxLines = numberOfCompletelyVisibleLines
}

@Deprecated("Better take avantage of doOnPreDraw()")
fun TextView.autoSize(autoSize: Boolean) {
    val autoSizeTextType = if (autoSize) TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM else TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE
    TextViewCompat.setAutoSizeTextTypeWithDefaults(this, autoSizeTextType)
}

fun TextView.underline(underline: Boolean) {
    val flags = if (underline) (paintFlags or Paint.DEV_KERN_TEXT_FLAG) else (paintFlags and Paint.DEV_KERN_TEXT_FLAG.inv())
    paintFlags = flags
}

fun TextView.strikeThrough(strikeThrough: Boolean) {
    val flags = if (strikeThrough) (paintFlags or Paint.STRIKE_THRU_TEXT_FLAG) else (paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
    paintFlags = flags
}

fun TextView.html(html: String) {
    text = if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html)
    }
}

fun TextView.linkify(urls: Boolean = true, emails: Boolean = true, phones: Boolean = true, addresses: Boolean = true) {
    var linkify = 0
    if (urls) linkify = linkify or Linkify.WEB_URLS
    if (emails) linkify = linkify or Linkify.EMAIL_ADDRESSES
    if (phones) linkify = linkify or Linkify.PHONE_NUMBERS
    if (addresses) {
        if (Build.VERSION.SDK_INT >= 28) {
            // TODO I just don't get how to use TextClassifier
        } else {
            @Suppress("DEPRECATION")
            linkify = linkify or Linkify.MAP_ADDRESSES
        }
    }
    val spannableString = SpannableString(text)
    Linkify.addLinks(spannableString, linkify)
    text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.clearPaintFlags() {
    paintFlags = 0
}
