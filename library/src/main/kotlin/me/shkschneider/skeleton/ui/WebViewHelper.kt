package me.shkschneider.skeleton.ui

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.extensions.simpleName
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger

object WebViewHelper {

    const val META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">"
    const val META_THEME = "<meta name=\"theme-color\" content=\"#a4c639\">"
    const val CHARSET = CharsetHelper.UTF8
    const val MIME_TYPE = MimeTypeHelper.TEXT_HTML

    fun get(): WebView {
        val webView = WebView(ContextHelper.applicationContext())
        if (Build.VERSION.SDK_INT >= 21) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        return webView
    }

    fun fromUrl(url: String): WebView {
        val webView = get()
        webView.loadUrl(url)
        return webView
    }

    fun fromAsset(asset: String): WebView {
        val webView = get()
        webView.loadDataWithBaseURL(FileHelper.PREFIX_ASSETS, asset, MIME_TYPE, CHARSET, "")
        return webView
    }

    fun fromRaw(raw: String): WebView {
        val webView = get()
        webView.loadDataWithBaseURL(FileHelper.join(FileHelper.PREFIX_RES, "raw"), raw, MIME_TYPE, CHARSET, "")
        return webView
    }

    fun fromHtml(source: String): WebView {
        val webView = get()
        webView.loadData(source, MIME_TYPE, CHARSET)
        return webView
    }

    @SuppressLint("AddJavascriptInterface") // Dangerous below API-17
    fun javascriptInterface(webView: WebView, javascriptInterface: JavaScriptInterface, name: String) {
        webView.addJavascriptInterface(javascriptInterface, name)
    }

    fun javascriptInterface(webView: WebView, javascriptInterface: JavaScriptInterface) {
        javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM)
    }

    fun back(webView: WebView): Boolean {
        if (! webView.canGoBack()) {
            Logger.info("WebView cannot go back")
            return false
        }

        webView.goBack()
        return true
    }

    fun forward(webView: WebView): Boolean {
        if (! webView.canGoForward()) {
            Logger.info("WebView cannot go forward")
            return false
        }

        webView.goForward()
        return true
    }

    fun original(webView: WebView): String {
        return webView.originalUrl
    }

    class JavaScriptInterface {

        @JavascriptInterface
        fun test() {
            Logger.debug(this::class.simpleName())
        }

    }

}
