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

import org.apache.http.HttpConnection;
import org.apache.http.HttpRequest;
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
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.LogHelper;

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
            if (mRequest != null) {
                if (! TextUtils.isEmpty(key)) {
                    if (! TextUtils.isEmpty(key)) {
                        mRequest.addHeader(key, value);
                    }
                    else {
                        LogHelper.w("Value was NULL");
                    }
                }
                else {
                    LogHelper.w("Key was NULL");
                }
            }
            else {
                LogHelper.w("Request was NULL");
            }
            return this;
        }

        public Builder head(final String url) {
            if (NetworkHelper.validUrl(url)) {
                mRequest = new HttpHead(url);
            }
            else {
                LogHelper.w("Url was invalid");
            }
            return this;
        }

        public Builder get(final String url) {
            if (NetworkHelper.validUrl(url)) {
                mRequest = new HttpGet(url);
            }
            else {
                LogHelper.w("Url was invalid");
            }
            return this;
        }

        public Builder post(final String url, final List<String[]> params) {
            if (NetworkHelper.validUrl(url)) {
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
                    ((HttpPost) mRequest).setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                }
                catch (IOException e) {
                    LogHelper.e("IOException: " + e.getMessage());
                }
            }
            else {
                LogHelper.w("Url was invalid");
            }
            return this;
        }

        public WebService build() {
            if (mClient != null) {
                if (mRequest != null) {
                    return new WebService(mClient, mRequest);
                }
                else {
                    LogHelper.w("Request was NULL");
                }
            }
            else {
                LogHelper.w("Client was NULL");
            }
            return null;
        }

    }

    @Override
    protected HttpResponse doInBackground(WebService.Callback... callbacks) {
        if (mClient != null) {
            if (mRequest != null) {
                try {
                    mResponse = mClient.execute(mRequest);
                }
                catch (IOException e) {
                    LogHelper.e("IOException: " + e.getMessage());
                }
            }
            else {
                LogHelper.w("Request was NULL");
            }
        }
        else {
            LogHelper.w("Client was NULL");
        }

        if (callbacks != null) {
            final Callback callback = callbacks[0];
            if (callback != null) {
                final Response webServiceResponse = new Response();
                if (mResponse != null) {
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
                }
                else {
                    LogHelper.w("Response was NULL");
                }
                callback.webServiceCallback((webServiceResponse.statusCode == HttpStatus.SC_OK), webServiceResponse);
            }
        }
        return mResponse;
    }

    public void run(final Callback callback) {
        execute(callback);
    }

    public void run() {
        execute();
    }

    public void cancel() {
        if (mRequest != null) {
            mRequest.abort();
        }
        else {
            LogHelper.w("Request was NULL");
        }
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
