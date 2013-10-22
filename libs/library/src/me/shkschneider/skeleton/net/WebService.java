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

import android.os.AsyncTask;
import android.text.TextUtils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.android.LogHelper;

@SuppressWarnings("unused")
public class WebService extends AsyncTask<WebService.Callback, Void, HttpResponse> {

    protected HttpClient mClient;
    protected HttpRequestBase mRequest;
    protected HttpResponse mResponse;

    public WebService(final HttpClient httpClient, final HttpRequestBase httpRequestBase) {
        mClient = httpClient;
        mRequest = httpRequestBase;
        mResponse = null;
    }

    public static class Builder {

        protected HttpClient mClient;
        protected HttpRequestBase mRequest;

        public Builder(final HttpClient httpClient) {
            mClient = httpClient;
            mRequest = null;
        }

        public Builder() {
            this(new DefaultHttpClient());
        }

        public Builder header(final String key, final String value) {
            if (mRequest == null) {
                LogHelper.w("Request was NULL");
            }
            else if (TextUtils.isEmpty(key)) {
                LogHelper.w("Key was NULL");
            }
            else if (TextUtils.isEmpty(value)) {
                LogHelper.w("Value was NULL");
            }
            else {
                mRequest.addHeader(key, value);
            }
            return this;
        }

        public Builder head(final String url) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.w("Url was invalid");
            }
            else {
                mRequest = new HttpHead(url);
            }
            return this;
        }

        public Builder get(final String url) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.w("Url was invalid");
            }
            else {
                mRequest = new HttpGet(url);
            }
            return this;
        }

        public Builder post(final String url, final List<String[]> params) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.w("Url was invalid");
            }
            else {
                mRequest = new HttpPost(url);
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
                    ((HttpPost) mRequest).setEntity(new UrlEncodedFormEntity(nameValuePairs, CharsetHelper.UTF8));

                }
                catch (IOException e) {
                    LogHelper.e("IOException: " + e.getMessage());
                }
            }
            return this;
        }

        public WebService build() {
            if (mClient == null) {
                LogHelper.w("Client was NULL");
                return null;
            }

            if (mRequest == null) {
                LogHelper.w("Request was NULL");
                return null;
            }

            return new WebService(mClient, mRequest);
        }

    }

    @Override
    protected HttpResponse doInBackground(WebService.Callback... callbacks) {
        if (mClient == null) {
            LogHelper.w("Client was NULL");
            return null;
        }

        if (mRequest == null) {
            LogHelper.w("Request was NULL");
            return null;
        }

        try {
            mResponse = mClient.execute(mRequest);
            if (mResponse == null) {
                LogHelper.w("Response was NULL");
                return null;
            }
        }
        catch (IOException e) {
            LogHelper.e("IOException: " + e.getMessage());
            return null;
        }

        final Response webServiceResponse = new Response();
        webServiceResponse.statusCode = mResponse.getStatusLine().getStatusCode();
        webServiceResponse.statusMessage = mResponse.getStatusLine().getReasonPhrase();
        try {
            if (mResponse.getEntity() != null) {
                webServiceResponse.response = mResponse.getEntity().getContent();
            }
        }
        catch (IOException e) {
            LogHelper.w("IOException: " + e.getMessage());
        }

        if (callbacks == null) {
            LogHelper.w("Callbacks was NULL");
            return mResponse;
        }

        final Callback callback = callbacks[0];
        if (callback == null) {
            LogHelper.w("Callback was NULL");
            return mResponse;
        }

        callback.webServiceCallback((webServiceResponse.statusCode == HttpStatus.SC_OK), webServiceResponse);
        return mResponse;
    }

    public void run(final Callback callback) {
        execute(callback);
    }

    public void run() {
        execute();
    }

    public void cancel() {
        if (mRequest == null) {
            LogHelper.w("Request was NULL");
            return ;
        }

        mRequest.abort();
    }

    public static class Response {

        public Integer statusCode;
        public String statusMessage;
        public InputStream response;

        public Response() {
            statusCode = -1;
            statusMessage = null;
            response = null;
        }

    }

    public static interface Callback {

        public void webServiceCallback(final Boolean success, final WebService.Response response);

    }

}
