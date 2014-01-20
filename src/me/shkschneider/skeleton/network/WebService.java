package me.shkschneider.skeleton.network;

import android.app.Activity;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.helpers.FileHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class WebService extends AsyncTask<WebService.Callback, Void, HttpResponse> {

    public static final int STATUS_OK = HttpStatus.SC_OK;

    private Activity mActivity;
    private HttpClient mClient;
    private HttpRequestBase mRequest;
    private HttpResponse mResponse;

    public static class Builder {

        private Activity mActivity;
        protected HttpClient mClient;
        protected HttpRequestBase mRequest;

        public Builder(final Activity activity, final HttpClient httpClient) {
            mActivity = activity;
            mClient = httpClient;
            mRequest = null;
        }

        public Builder(final Activity activity) {
            this(activity, new DefaultHttpClient());
        }

        public Builder header(final String key, final String value) {
            if (mRequest == null) {
                LogHelper.warning("Request was NULL");
                return this;
            }
            else if (StringHelper.nullOrEmpty(key)) {
                LogHelper.warning("Key was NULL");
                return this;
            }
            else if (StringHelper.nullOrEmpty(value)) {
                LogHelper.warning("Value was NULL");
                return this;
            }
            else {
                mRequest.addHeader(key, value);
            }
            return this;
        }

        public Builder head(final String url) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.warning("Url was invalid");
                return this;
            }
            else {
                mRequest = new HttpHead(url);
            }
            return this;
        }

        public Builder get(final String url) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.warning("Url was invalid");
                return this;
            }
            else {
                mRequest = new HttpGet(url);
            }
            return this;
        }

        public Builder post(final String url, final List<String[]> params) {
            if (! NetworkHelper.validUrl(url)) {
                LogHelper.warning("Url was invalid");
                return this;
            }
            else {
                mRequest = new HttpPost(url);
                try {
                    final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    for (final String[] param : params) {
                        if (param.length != 2) {
                            LogHelper.warning("Params are invalid");
                        }
                        else {
                            nameValuePairs.add(new BasicNameValuePair(param[0], param[1]));
                        }
                    }
                    ((HttpPost) mRequest).setEntity(new UrlEncodedFormEntity(nameValuePairs, CharsetHelper.UTF8));

                }
                catch (final IOException e) {
                    LogHelper.error("IOException: " + e.getMessage());
                    return this;
                }
            }
            return this;
        }

        public WebService build() {
            if (mClient == null) {
                LogHelper.warning("Client was NULL");
                return null;
            }
            if (mRequest == null) {
                LogHelper.warning("Request was NULL");
                return null;
            }

            return new WebService(mActivity, mClient, mRequest);
        }

    }

    public WebService(final Activity activity, final HttpClient httpClient, final HttpRequestBase httpRequestBase) {
        mActivity = activity;
        mClient = httpClient;
        mRequest = httpRequestBase;
        mResponse = null;
    }

    @Override
    protected HttpResponse doInBackground(final WebService.Callback... callbacks) {
        if (mClient == null) {
            LogHelper.warning("Client was NULL");
            return null;
        }
        if (mRequest == null) {
            LogHelper.warning("Request was NULL");
            return null;
        }

        try {
            mResponse = mClient.execute(mRequest);
            if (mResponse == null) {
                LogHelper.warning("Response was NULL");
                return null;
            }
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return null;
        }

        final Response webServiceResponse = new Response();
        webServiceResponse.statusCode = mResponse.getStatusLine().getStatusCode();
        webServiceResponse.statusMessage = mResponse.getStatusLine().getReasonPhrase();
        try {
            final HttpEntity httpEntity = mResponse.getEntity();
            if (httpEntity != null && httpEntity.getContentType().toString().startsWith("Content-Type: text/")) {
                webServiceResponse.content = FileHelper.readString(httpEntity.getContent());
            }
        }
        catch (final IOException e) {
            LogHelper.error("IOException: " + e.getMessage());
            return null;
        }
        catch (final IllegalStateException e) {
            LogHelper.error("IllegalStateException: " + e.getMessage());
            return null;
        }

        if (callbacks == null) {
            LogHelper.info("Callbacks was NULL");
            return mResponse;
        }

        final Callback callback = callbacks[0];
        if (callback == null) {
            LogHelper.info("Receiver was NULL");
            return mResponse;
        }

        // Running view-related tasks from this callback outside of the UI-thread might lead to FC
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    callback.webServiceCallback((webServiceResponse.statusCode == HttpStatus.SC_OK), webServiceResponse);
                }

            });
        }
        else {
            callback.webServiceCallback((webServiceResponse.statusCode == HttpStatus.SC_OK), webServiceResponse);
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
        if (mRequest == null) {
            LogHelper.info("Request was NULL");
            return ;
        }

        mRequest.abort();
    }

    public static class Response {

        public int statusCode;
        public String statusMessage;
        public String content;

        public Response() {
            statusCode = -1;
            statusMessage = null;
            content = null;
        }

    }

    public static interface Callback {

        public void webServiceCallback(final boolean success, final WebService.Response response);

    }

}
