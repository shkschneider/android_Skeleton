package me.shkschneider.skeleton.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.data.GsonParser;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;

// <http://developer.android.com/reference/java/net/HttpURLConnection.html>
public class WebService extends AsyncTask<Void, Void, Object> {

    private Method mMethod;
    private String mUrl;
    private Map<String, String> mBody;
    private Callback mCallback;

    public WebService(@NonNull final Method method, @NonNull final String url, @Nullable final Map<String, String> body, @Nullable final Callback callback) {
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

    @Nullable
    public Map<String, String> body() {
        return mBody;
    }

    public void run() {
        execute();
    }

    public void cancel() {
        cancel(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(@Size(0) @Nullable final Void ... voids) {
        // OkHttp
        HttpURLConnection httpURLConnection = null;
        try {
            // URL
            final URL url = new URL(mUrl);
            // Connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // httpURLConnection.setConnectTimeout(TIMEOUT_CONNECT);
            // httpURLConnection.setReadTimeout(TIMEOUT_READ);
            // Method
            final String method = mMethod.toString();
            if (method == null) {
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Bad method");
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(httpURLConnection.getRequestMethod().equals("POST"));
            // Cache
            // httpURLConnection.addRequestProperty("Cache-Control", (CACHE < 0 ? "no-cache" : "max-stale=" + CACHE));
            // Parameters
            if (mBody != null) {
//                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
//                String params = "";
//                for (final String key : mBody.keySet()) {
//                    if (! StringHelper.nullOrEmpty(params)) {
//                        params += "&";
//                    }
//                    params += key + "=" + UrlHelper.encode(mBody.get(key));
//                }
//                dataOutputStream.write(params.getBytes(CharsetHelper.UTF8));
                final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                final JSONObject body = new JSONObject(mBody);
                outputStreamWriter.write(body.toString());
                outputStreamWriter.close();
            }
            // Response
            final int responseCode = httpURLConnection.getResponseCode();
            final String responseMessage = httpURLConnection.getResponseMessage();
            LogHelper.debug(String.format(LocaleHelper.locale(), "%d: %s", responseCode, responseMessage));
            InputStream inputStream;
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_ACCEPTED:
                    if (httpURLConnection.getInputStream() == null) {
                        return null;
                    }
                    // Buffered for performance
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    return GsonParser.parse(inputStream);
                default:
                    if (httpURLConnection.getErrorStream() == null) {
                        return new WebServiceException(responseCode, responseMessage);
                    }
                    // Buffered for performance
                    inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                    return new WebServiceException(responseCode, FileHelper.readString(inputStream));
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return new WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.getClass()));
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(@Nullable final Object object) {
        // Should not happen
        if (object == null) {
            LogHelper.warning("Nothing");
            final WebServiceException webServiceException = new WebServiceException(WebServiceException.INTERNAL_ERROR, "Nothing");
            mCallback.webServiceCallback(webServiceException, null);
        }
        // Could happen
        else if (object instanceof WebServiceException) {
            final WebServiceException webServiceException = (WebServiceException) object;
            mCallback.webServiceCallback(webServiceException, null);
        }
        // Should happen
        else if (object instanceof JsonObject) {
            final JsonObject jsonObject = (JsonObject) object;
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

        void webServiceCallback(@Nullable final WebServiceException e, @Nullable final JsonObject jsonObject);

    }

}
