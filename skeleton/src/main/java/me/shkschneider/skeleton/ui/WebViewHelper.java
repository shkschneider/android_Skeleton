package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import org.apache.http.protocol.HTTP;

public class WebViewHelper {

    public static final String META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">";
    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static WebView fix(@NonNull final WebView webView) {
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return webView;
    }

    public static WebView fromUrl(@NonNull final String url) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadUrl(url);
        return fix(webView);
    }

    public static WebView fromAsset(@NonNull final String asset) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
        return fix(webView);
    }

    public static WebView fromHtml(@NonNull final String source) {
        final WebView webView = new WebView(SkeletonApplication.CONTEXT);
        webView.loadData(source, MIME_TYPE, CHARSET);
        return fix(webView);
    }

    // FIXME WebView.addJavascriptInterface should not be called with minSdkVersion < 17 for security reasons
    public static boolean javascriptInterface(@NonNull final WebView webView, @NonNull final Object javascriptInterface, @NonNull final String name) {
        webView.addJavascriptInterface(javascriptInterface, name);
        return true;
    }

    public static boolean javascriptInterface(@NonNull final WebView webView, @NonNull final Object javascriptInterface) {
        return javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
    }

    public static boolean back(@NonNull final WebView webView) {
        if (! webView.canGoBack()) {
            LogHelper.info("WebView cannot go back");
            return false;
        }

        webView.goBack();
        return true;
    }

    public static boolean forward(@NonNull final WebView webView) {
        if (! webView.canGoForward()) {
            LogHelper.info("WebView cannot go forward");
            return false;
        }

        webView.goForward();
        return true;
    }

    public static String original(@NonNull final WebView webView) {
        return webView.getOriginalUrl();
    }

}
