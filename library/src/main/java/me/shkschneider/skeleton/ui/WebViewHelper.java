package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.data.MimeTypeHelper;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.java.SkHide;

public class WebViewHelper {

    protected WebViewHelper() {
        // Empty
    }

    @SkHide
    public static final String META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">";
    @SkHide
    public static final String META_THEME = "<meta name=\"theme-color\" content=\"#a4c639\">";
    @SkHide
    public static final String CHARSET = CharsetHelper.UTF8;
    @SkHide
    public static final String MIME_TYPE = MimeTypeHelper.TEXT_HTML;

    public static WebView get() {
        final WebView webView = new WebView(ContextHelper.applicationContext());
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        return webView;
    }

    public static WebView fromUrl(@NonNull final String url) {
        final WebView webView = get();
        webView.loadUrl(url);
        return webView;
    }

    public static WebView fromAsset(@NonNull final String asset) {
        final WebView webView = get();
        webView.loadDataWithBaseURL(FileHelper.PREFIX_ASSETS, asset, MIME_TYPE, CHARSET, "");
        return webView;
    }

    public static WebView fromRaw(@NonNull final String raw) {
        final WebView webView = get();
        webView.loadDataWithBaseURL(FileHelper.join(FileHelper.PREFIX_RES, "raw"), raw, MIME_TYPE, CHARSET, "");
        return webView;
    }

    public static WebView fromHtml(@NonNull final String source) {
        final WebView webView = get();
        webView.loadData(source, MIME_TYPE, CHARSET);
        return webView;
    }

    @SuppressLint("AddJavascriptInterface") // Dangerous below API-17
    public static void javascriptInterface(@NonNull final WebView webView, @NonNull final JavaScriptInterface javascriptInterface, @NonNull final String name) {
        webView.addJavascriptInterface(javascriptInterface, name);
    }

    public static void javascriptInterface(@NonNull final WebView webView, @NonNull final JavaScriptInterface javascriptInterface) {
        javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
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

    public static class JavaScriptInterface {

        @JavascriptInterface
        public void test() {
            LogHelper.debug(ClassHelper.simpleName(this.getClass()));
        }

    }

}
