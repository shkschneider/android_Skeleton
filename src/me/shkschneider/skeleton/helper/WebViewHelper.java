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

import org.apache.http.protocol.HTTP;

@SuppressWarnings("unused")
public class WebViewHelper {

    public static final String CHARSET = HTTP.UTF_8;
    public static final String MIME_TYPE = "text/html";

    public static android.webkit.WebView fromUrl(final Context context, final String url) {
        if (context != null) {
            final android.webkit.WebView webView = new android.webkit.WebView(context);
            webView.loadUrl(url);
            return webView;
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

    public static android.webkit.WebView fromAsset(final Context context, final String asset) {
        if (context != null) {
            final android.webkit.WebView webView = new android.webkit.WebView(context);
            webView.loadDataWithBaseURL(FileHelper.ASSETS_PREFIX, asset, MIME_TYPE, CHARSET, "");
            return webView;
        }
        else {
            LogHelper.w("Context was NULL");
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
            LogHelper.w("Context was NULL");
        }
        return null;
    }

}
