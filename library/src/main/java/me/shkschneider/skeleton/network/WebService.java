package me.shkschneider.skeleton.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.data.GsonParser;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.java.MapHelper;
import me.shkschneider.skeleton.java.StringHelper;

// http://developer.android.com/reference/java/net/HttpURLConnection.html
public class WebService extends AsyncTask<WebService.Callback, Void, Object> {

    private static final int TIMEOUT_CONNECT = (int) TimeUnit.SECONDS.toMillis(3);
    private static final int TIMEOUT_READ = (int) TimeUnit.SECONDS.toMillis(5);
    private static final int CACHE = (int) TimeUnit.HOURS.toMillis(24);

    private Method mMethod;
    private String mUrl;
    private Map<String, String> mBody;
    private Callback mCallback;

    public WebService(@NonNull final Method method, @NonNull final String url, final Map<String, String> body, final Callback callback) {
        mMethod = method;
        mUrl = url;
        mBody = body;
        mCallback = callback;
    }

    public String method() {
        return mMethod.toString();
    }

    public String url() {
        return mUrl;
    }

    public Map<String, String> body() {
        return mBody;
    }

    public void run() {
        execute(mCallback);
    }

    public void cancel() {
        cancel(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(final Callback... callbacks) {
        // OkHttp
        HttpURLConnection httpURLConnection = null;
        try {
            // URL
            final URL url = new URL(mUrl);
            // Connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // Timeouts
            httpURLConnection.setConnectTimeout(TIMEOUT_CONNECT);
            httpURLConnection.setReadTimeout(TIMEOUT_READ);
            // Method
            final String method = mMethod.toString();
            if (method == null) {
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Bad method");
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(httpURLConnection.getRequestMethod().equals("POST"));
            // Cache
            httpURLConnection.addRequestProperty("Cache-Control", (CACHE < 0 ? "no-cache" : "max-stale=" + CACHE));
            // Parameters
            if (mBody != null) {
                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                String params = "";
                for (final String key : mBody.keySet()) {
                    if (! StringHelper.nullOrEmpty(params)) {
                        params += "&";
                    }
                    params += key + "=" + UrlHelper.encode(mBody.get(key));
                }
                dataOutputStream.write(params.getBytes(CharsetHelper.UTF8));
            }
            // Buffered for performance
            final InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            // Response
            final int responseCode = httpURLConnection.getResponseCode();
            final String responseMessage = httpURLConnection.getResponseMessage();
            LogHelper.debug(String.format(Locale.US, "%d: %s", responseCode, responseMessage));
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_ACCEPTED:
                    return GsonParser.parse(inputStream);
                default:
                    return new WebServiceException(responseCode, responseMessage);
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return new WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.getClass()));
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(final Object o) {
        // Should not happen
        if (o == null) {
            LogHelper.warning("Nothing");
            final WebServiceException webServiceException = new WebServiceException(WebServiceException.INTERNAL_ERROR, "Nothing");
            mCallback.webServiceCallback(webServiceException, null);
        }
        // Could happen
        else if (o instanceof WebServiceException) {
            final WebServiceException webServiceException = (WebServiceException) o;
            mCallback.webServiceCallback(webServiceException, null);
        }
        // Should happen
        else if (o instanceof JsonObject) {
            final JsonObject jsonObject = (JsonObject) o;
            mCallback.webServiceCallback(null, jsonObject);
        }
        // Should never happen
        else {
            LogHelper.error("Invalid");
            final WebServiceException webServiceException = new WebServiceException(WebServiceException.INTERNAL_ERROR, "Invalid");
            mCallback.webServiceCallback(webServiceException, null);
        }
    }

    public enum Method {

        GET,
        POST;

        @Override
        public String toString() {
            switch (this) {
                case GET:
                    return "GET";
                case POST:
                    return "POST";
                default:
                    return null;
            }
        }

    }

    public interface Callback {

        void webServiceCallback(final WebServiceException e, final JsonObject jsonObject);

    }

}
