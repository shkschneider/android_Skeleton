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
package me.shkschneider.skeleton.net;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class Requests {

    private static HttpClient httpClient = new DefaultHttpClient();

    public static HttpResponse head(final String url) {
        if (NetworkHelper.validUrl(url)) {
            final HttpHead httpHead = new HttpHead(url);
            try {
                return httpClient.execute(httpHead);
            }
            catch (ClientProtocolException e) {
                LogHelper.e("ClientProtocolException: " + e.getMessage());
            }
            catch (IOException e) {
                LogHelper.e("IOException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("Url was invalid");
        }
        return null;
    }

    public static HttpResponse get(final String url) {
        if (NetworkHelper.validUrl(url)) {
            final HttpGet httpGet = new HttpGet(url);
            try {
                return httpClient.execute(httpGet);
            }
            catch (ClientProtocolException e) {
                LogHelper.e("ClientProtocolException: " + e.getMessage());
            }
            catch (IOException e) {
                LogHelper.e("IOException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("Url was invalid");
        }
        return null;
    }

    public static HttpResponse post(final String url, final List<String[]> params) {
        if (NetworkHelper.validUrl(url)) {
            final HttpPost httpPost = new HttpPost(url);
            try {
                final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                for (final String[] param : params) {
                    if (param.length != 2) {
                        LogHelper.w("Params are invalid");
                    }
                    else {
                        nameValuePairs.add(new BasicNameValuePair(param[0], param[1]));
                    }
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                return httpClient.execute(httpPost);

            }
            catch (ClientProtocolException e) {
                LogHelper.e("ClientProtocolException: " + e.getMessage());
            }
            catch (IOException e) {
                LogHelper.e("IOException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("Url was invalid");
        }
        return null;
    }

    public static InputStream inputStream(final HttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                return httpResponse.getEntity().getContent();
            }
            catch (IOException e) {
                LogHelper.w("IOException: " + e.getMessage());
            }
        }
        else {
            LogHelper.w("HttpResponse was NULL");
        }
        return null;
    }

    public static String string(final HttpResponse httpResponse) {
        return FileHelper.readString(inputStream(httpResponse));
    }

}
