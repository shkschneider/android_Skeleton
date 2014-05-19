package me.shkschneider.skeleton.ui;

import android.webkit.WebView;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import org.apache.http.protocol.HTTP;
import org.jetbrains.annotations.NotNull;

public class WebViewHelper {

    public static final String META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">";
    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static WebView fromUrl(@NotNull final String url) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadUrl(url);
        return webView;
    }

    public static WebView fromAsset(@NotNull final String asset) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
        return webView;
    }

    public static WebView fromHtml(@NotNull final String source) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadData(source, MIME_TYPE, CHARSET);
        return webView;
    }

    public static boolean javascriptInterface(@NotNull final WebView webView, @NotNull final Object javascriptInterface, @NotNull final String name) {
        webView.addJavascriptInterface(javascriptInterface, name);
        return true;
    }

    public static boolean javascriptInterface(@NotNull final WebView webView, @NotNull final Object javascriptInterface) {
        return javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
    }

    public static boolean back(@NotNull final WebView webView) {
        if (! webView.canGoBack()) {
            LogHelper.info("WebView cannot go back");
            return false;
        }

        webView.goBack();
        return true;
    }

    public static boolean forward(@NotNull final WebView webView) {
        if (! webView.canGoForward()) {
            LogHelper.info("WebView cannot go forward");
            return false;
        }

        webView.goForward();
        return true;
    }

    public static String original(@NotNull final WebView webView) {
        return webView.getOriginalUrl();
    }

}
