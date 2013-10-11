/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;

import org.apache.http.protocol.HTTP;

@SuppressWarnings("unused")
public class WebViewHelper {

    public static final String META_VIEWPORT = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">";
    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static WebView fromUrl(final Context context, final String url) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        final WebView webView = new WebView(context);
        webView.loadUrl(url);
        return webView;
    }

    public static WebView fromAsset(final Context context, final String asset) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        final WebView webView = new WebView(context);
        webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
        return webView;
    }

    public static WebView fromHtml(final Context context, final String source) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        final WebView webView = new WebView(context);
        webView.loadData(source, MIME_TYPE, CHARSET);
        return webView;
    }

    public static Boolean javascriptInterface(final WebView webView, final Object javascriptInterface, final String name) {
        if (webView == null) {
            LogHelper.w("WebView was NULL");
            return false;
        }

        if (javascriptInterface == null) {
            LogHelper.w("JavascriptInterface was NULL");
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            LogHelper.w("Name was NULL");
            return false;
        }

        webView.addJavascriptInterface(javascriptInterface, name);
        // onClick="Android.alert()" -> @JavascriptInterface public void JavascriptInterface.alert()
        return true;
    }

    public static Boolean javascriptInterface(final WebView webView, final Object javascriptInterface) {
        return javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
    }

    public static Boolean back(final WebView webView) {
        if (webView == null) {
            LogHelper.w("WebView was NULL");
            return false;
        }

        if (! webView.canGoBack()) {
            LogHelper.w("WebView cannot go back");
        }

        webView.goBack();
        return true;
    }

    public static Boolean forward(final WebView webView) {
        if (webView == null) {
            LogHelper.w("WebView was NULL");
            return false;
        }

        if (! webView.canGoForward()) {
            LogHelper.w("WebView cannot go forward");
        }

        webView.goForward();
        return true;
    }

}
