package me.shkschneider.skeleton.extensions.android

import android.os.Build
import android.view.View
import android.webkit.WebView
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.log.Logger

object WebViewHelper {

    const val META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">"
    const val META_THEME = "<meta name=\"theme-color\" content=\"#a4c639\">"
    const val CHARSET = CharsetHelper.UTF8
    const val MIME_TYPE = MimeTypeHelper.TEXT_HTML

    private fun get(): WebView =
            WebView(ContextHelper.applicationContext()).apply {
                if (Build.VERSION.SDK_INT >= 21) {
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                }
            }

    fun fromUrl(url: String): WebView =
            get().apply {
                loadUrl(url)
            }

    fun fromAsset(asset: String): WebView =
            get().apply {
                loadDataWithBaseURL(FileHelper.PREFIX_ASSETS, asset, WebViewHelper.MIME_TYPE, WebViewHelper.CHARSET, "")
            }

    fun fromRaw(raw: String): WebView =
            get().apply {
                loadDataWithBaseURL(FileHelper.join(FileHelper.PREFIX_RES, "raw"), raw, WebViewHelper.MIME_TYPE, WebViewHelper.CHARSET, "")
            }

    fun fromHtml(source: String): WebView =
            get().apply {
                loadData(source, WebViewHelper.MIME_TYPE, WebViewHelper.CHARSET)
            }

}

fun WebView.backward(): Boolean {
    if (!canGoBack()) {
        Logger.info("WebView cannot go backward")
        return false
    }
    goBack()
    return true
}

fun WebView.forward(): Boolean {
    if (!canGoForward()) {
        Logger.info("WebView cannot go forward")
        return false
    }
    goForward()
    return true
}
