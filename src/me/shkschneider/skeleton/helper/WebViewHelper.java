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
        if (context != null) {
            final WebView webView = new WebView(context);
            webView.loadUrl(url);
            return webView;
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

    public static WebView fromAsset(final Context context, final String asset) {
        if (context != null) {
            final WebView webView = new WebView(context);
            webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
            return webView;
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

    public static WebView fromHtml(final Context context, final String source) {
        if (context != null) {
            final WebView webView = new WebView(context);
            webView.loadData(source, MIME_TYPE, CHARSET);
            return webView;
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

    public static Boolean javascriptInterface(final WebView webView, final Object javascriptInterface, final String name) {
        if (webView != null) {
            if (javascriptInterface != null) {
                if (! TextUtils.isEmpty(name)) {
                    webView.addJavascriptInterface(javascriptInterface, name);
                    // onClick="Android.alert()" -> @JavascriptInterface public void JavascriptInterface.alert()
                    return true;
                }
                else {
                    LogHelper.w("Name was NULL");
                }
            }
            else {
                LogHelper.w("JavascriptInterface was NULL");
            }
        }
        else {
            LogHelper.w("WebView was NULL");
        }
        return false;
    }

    public static Boolean javascriptInterface(final WebView webView, final Object javascriptInterface) {
        return javascriptInterface(webView, javascriptInterface, AndroidHelper.PLATFORM);
    }

    public static Boolean back(final WebView webView) {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            else {
                LogHelper.w("WebView cannot go back");
            }
        }
        else {
            LogHelper.w("WebView was NULL");
        }
        return false;
    }

    public static Boolean forward(final WebView webView) {
        if (webView != null) {
            if (webView.canGoForward()) {
                webView.goForward();
                return true;
            }
            else {
                LogHelper.w("WebView cannot go forward");
            }
        }
        else {
            LogHelper.w("WebView was NULL");
        }
        return false;
    }

}
