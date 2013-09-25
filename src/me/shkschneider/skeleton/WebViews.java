package me.shkschneider.skeleton;

import android.content.Context;

import org.apache.http.protocol.HTTP;

public class WebViews {

    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static android.webkit.WebView fromUrl(final Context context, final String url) {
        if (context != null) {
            final android.webkit.WebView webView = new android.webkit.WebView(context);
            webView.loadUrl(url);
            return webView;
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static android.webkit.WebView fromAsset(final Context context, final String asset) {
        if (context != null) {
            final android.webkit.WebView webView = new android.webkit.WebView(context);
            webView.loadDataWithBaseURL(Skeleton.File.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
            return webView;
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static android.webkit.WebView fromHtml(final Context context, final String source) {
        if (context != null) {
            final android.webkit.WebView webView = new android.webkit.WebView(context);
            webView.loadData(source, MIME_TYPE, CHARSET);
            return webView;
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

}
