package me.shkschneider.skeleton.ui;

import android.webkit.WebView;

import org.apache.http.protocol.HTTP;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class WebViewHelper {

    // <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">

    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static WebView fromUrl(final String url) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadUrl(url);
        return webView;
    }

    public static WebView fromAsset(final String asset) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
        return webView;
    }

    public static WebView fromHtml(final String source) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadData(source, MIME_TYPE, CHARSET);
        return webView;
    }

    public static boolean javascriptInterface(final WebView webView, final Object javascriptInterface, final String name) {
        if (webView == null) {
            LogHelper.warning("WebView was NULL");
            return false;
        }
        if (javascriptInterface == null) {
            LogHelper.warning("JavascriptInterface was NULL");
            return false;
        }
        if (StringHelper.nullOrEmpty(name)) {
            LogHelper.warning("Name was NULL");
            return false;
        }

        webView.addJavascriptInterface(javascriptInterface, name);
        return true;
    }

    public static boolean javascriptInterface(final WebView webView, final Object javascriptInterface) {
        return javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
    }

    public static boolean back(final WebView webView) {
        if (webView == null) {
            LogHelper.warning("WebView was NULL");
            return false;
        }
        if (! webView.canGoBack()) {
            LogHelper.info("WebView cannot go back");
            return false;
        }

        webView.goBack();
        return true;
    }

    public static boolean forward(final WebView webView) {
        if (webView == null) {
            LogHelper.warning("WebView was NULL");
            return false;
        }
        if (! webView.canGoForward()) {
            LogHelper.info("WebView cannot go forward");
            return false;
        }

        webView.goForward();
        return true;
    }

    public static String original(final WebView webView) {
        if (webView == null) {
            LogHelper.warning("WebView was NULL");
            return null;
        }

        return webView.getOriginalUrl();
    }

}
