package me.shkschneider.skeleton.extensions

import android.view.View
import android.webkit.WebView
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.log.Logger

object WebViewHelper {

    const val META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">"
    const val META_THEME = "<meta name=\"theme-color\" content=\"#a4c639\">"
    const val CHARSET = CharsetHelper.UTF8
    const val MIME_TYPE = MimeTypeHelper.TEXT_HTML

    private fun get(): WebView =
            WebView(ContextHelper.applicationContext()).apply {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }

    fun fromUrl(url: String): WebView =
            get().apply {
                loadUrl(url)
            }

    fun fromAsset(asset: String): WebView =
            get().apply {
                loadDataWithBaseURL(FileHelper.PREFIX_ASSETS, asset, MIME_TYPE, CHARSET, "")
            }

    fun fromRaw(raw: String): WebView =
            get().apply {
                loadDataWithBaseURL(FileHelper.join(FileHelper.PREFIX_RES, "raw"), raw, MIME_TYPE, CHARSET, "")
            }

    fun fromHtml(source: String): WebView =
            get().apply {
                loadData(source, MIME_TYPE, CHARSET)
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
