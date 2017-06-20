package me.shkschneider.skeleton.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.data.MimeTypeHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;

public class WebService {

    private Method mMethod;
    private String mUrl;
    private Class mType;
    private Map<String, String> mHeaders;
    private Map<String, String> mBody;
    private Callback mCallback;

    public WebService(@NonNull final Method method, @NonNull final String url, @Nullable final Callback callback) {
        mMethod = method;
        mUrl = url;
        mCallback = callback;
    }

    public WebService as(final Class type) {
        mType = type;
        return this;
    }

    public WebService headers(@Nullable final Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    public WebService body(@Nullable final Map<String, String> body) {
        mBody = body;
        return this;
    }

    public void getAs(final Class type) {
        mType = type;
        new Task().execute();
    }

    public void get() {
        getAs(JsonElement.class);
    }

    // <https://www.ietf.org/rfc/rfc2616.txt>
    public enum Method {

        // OPTIONS
        GET,
        // HEAD
        POST,
        PUT,
        DELETE;
        // TRACE
        // CONNECT

        public boolean allowsBody() {
            switch (this) {
                case POST:
                case PUT:
                case DELETE: // Although SHOULD be ignored
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public String toString() {
            return name().toUpperCase();
        }

    }

    private class Task extends AsyncTask<Void, Void, Object> {

        private static final int TIMEOUT_CONNECT = 15000;
        private static final int TIMEOUT_READ = 60000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(@Size(0) @Nullable final Void... voids) {
            HttpURLConnection httpURLConnection = null;
            try {
                final URL url = new URL(mUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(TIMEOUT_CONNECT);
                httpURLConnection.setReadTimeout(TIMEOUT_READ);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                if (mMethod.name() == null) {
                    return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Bad method");
                }

                if (mHeaders != null) {
                    for (final Map.Entry<String, String> entry : mHeaders.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                final boolean allowsBody = mMethod.allowsBody();
                httpURLConnection.setRequestMethod(mMethod.name());
                httpURLConnection.setDoOutput(allowsBody);
                if (! allowsBody && mBody != null) {
                    return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Body not allowed");
                }
                if (mBody != null) {
                    httpURLConnection.setRequestProperty("Content-Type", MimeTypeHelper.APPLICATION_FORMURLENCODED);
                    final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    String params = "";
                    for (final String key : mBody.keySet()) {
                        if (! TextUtils.isEmpty(params)) params += "&";
                        params += key + "=" + UrlHelper.encode(mBody.get(key));
                    }
                    LogHelper.verbose("=> " + params);
                    final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8));
                    bufferedWriter.write(params);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    dataOutputStream.close();
                }

                final int responseCode = httpURLConnection.getResponseCode();
                final String responseMessage = httpURLConnection.getResponseMessage();
                LogHelper.debug("<- " + responseCode + " " + responseMessage);
                final InputStream errorStream = httpURLConnection.getErrorStream();
                if (errorStream != null) {
                    final String body = FileHelper.readString(errorStream);
                    if (! TextUtils.isEmpty(body)) {
                        LogHelper.verbose("<= " + body);
                        return new WebServiceException(responseCode, body);
                    }
                    return new WebServiceException(responseCode, responseMessage);
                }
                final InputStream inputStream = httpURLConnection.getInputStream();
                final String body = FileHelper.readString(inputStream);
                LogHelper.verbose("<= " + body);
                if (TextUtils.isEmpty(body)) {
                    return "";
                }
                else {
                    final Object result = new Gson().fromJson(body, mType);
                    if (result != null) {
                        return result;
                    }
                }
                // WebServiceException but 200 OK means bad JSON
                return new WebServiceException(responseCode, responseMessage);
            }
            catch (final JsonSyntaxException e) {
                LogHelper.wtf(e);
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.getClass()));
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

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(@Nullable final Object result) {
            super.onPostExecute(result);
            if (result instanceof WebServiceException) {
                mCallback.failure((WebServiceException) result);
            }
            else if (result == null || ClassHelper.canonicalName(result.getClass()).equals(ClassHelper.canonicalName(mType))) {
                mCallback.success(result);
            }
        }

    }

    public interface Callback<T> {

        void success(@Nullable final T result);
        void failure(@NonNull final WebServiceException e);

    }


}
