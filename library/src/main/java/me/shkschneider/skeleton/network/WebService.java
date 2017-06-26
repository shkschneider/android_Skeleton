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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.data.MimeTypeHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.java.SkHide;

public class WebService {

    @Deprecated
    public static WebService options(@NonNull final String url) {
        throw new UnsupportedOperationException(Method.OPTIONS.name());
    }

    public static WebService get(@NonNull final String url) {
        return new WebService(Method.GET, url);
    }

    @Deprecated
    public static WebService head(@NonNull final String url) {
        throw new UnsupportedOperationException(Method.HEAD.name());
    }

    public static WebService post(@NonNull final String url) {
        return new WebService(Method.POST, url);
    }

    public static WebService put(@NonNull final String url) {
        return new WebService(Method.PUT, url);
    }

    public static WebService delete(@NonNull final String url) {
        return new WebService(Method.DELETE, url);
    }

    @Deprecated
    public static WebService trace(@NonNull final String url) {
        throw new UnsupportedOperationException(Method.TRACE.name());
    }

    @Deprecated
    public static WebService connect(@NonNull final String url) {
        throw new UnsupportedOperationException(Method.CONNECT.name());
    }

    private final Method mMethod;
    private String mUrl;
    private Class mType;
    private Map<String, String> mHeaders;
    private Map<String, String> mBody;
    private Callback mCallback;

    private WebService(@NonNull final WebService.Method method, @NonNull final String url) {
        mMethod = method;
        mUrl = url;
        mType = JsonElement.class;
    }

    public WebService url(@NonNull final String url) {
        mUrl = url;
        return this;
    }

    @SkHide
    public String url() {
        return mUrl;
    }

    public WebService headers(@Nullable final Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    @SkHide
    public Map<String, String> headers() {
        return mHeaders;
    }

    public WebService body(@Nullable final Map<String, String> body) {
        mBody = body;
        return this;
    }

    @SkHide
    public Map<String, String> body() {
        return mBody;
    }

    public <T> WebService callback(@Nullable final WebService.Callback<T> callback) {
        mCallback = callback;
        return this;
    }

    @SkHide
    public Callback callback() {
        return mCallback;
    }

    public WebService as(final Class type) {
        mType = type;
        return this;
    }

    @SkHide
    public Class as() {
        return mType;
    }

    public void run() {
        new Task().execute();
    }

    // <https://www.ietf.org/rfc/rfc2616.txt>
    private enum Method {

        OPTIONS,
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        TRACE,
        CONNECT;

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
                    final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8));
                    bufferedWriter.write(params);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    dataOutputStream.close();
                }

                LogHelper.debug("=> " + mMethod.name() + " " + mUrl + " " + (mHeaders != null ? mHeaders.toString() : "{}") + " " + (mBody != null ? mBody.toString() : "{}"));
                final int responseCode = httpURLConnection.getResponseCode();
                final String responseMessage = httpURLConnection.getResponseMessage();
                LogHelper.debug("<= " + responseCode + " " + responseMessage + " " + mUrl);
                final InputStream errorStream = httpURLConnection.getErrorStream();
                if (errorStream != null) {
                    final String body = FileHelper.readString(errorStream);
                    if (! TextUtils.isEmpty(body)) {
                        LogHelper.verbose("<- " + body);
                        return new WebServiceException(responseCode, body);
                    }
                    return new WebServiceException(responseCode, responseMessage);
                }
                final InputStream inputStream = httpURLConnection.getInputStream();
                final String body = FileHelper.readString(inputStream);
                LogHelper.verbose("<- " + body);
                if (TextUtils.isEmpty(body)) {
                    return "";
                }
                else {
                    final Object result = new Gson().fromJson(body, mType);
                    if (result != null) {
                        return result;
                    }
                }
                return new WebServiceException(responseCode, responseMessage);
            }
            catch (final JsonSyntaxException e) {
                LogHelper.wtf(e);
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.getClass()));
            }
            catch (final MalformedURLException e) {
                LogHelper.wtf(e);
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.getClass()));
            }
            catch (final IOException e) {
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
            else if (result == null) {
                mCallback.success(null);
            }
            else if (ClassHelper.canonicalName(result.getClass()).equals(ClassHelper.canonicalName(mType))) {
                mCallback.success(result);
            }
            else {
                mCallback.failure(new WebServiceException(WebServiceException.INTERNAL_ERROR,
                        ClassHelper.simpleName(result.getClass()) + " != " + ClassHelper.simpleName(mType)));
            }
        }

    }

    public interface Callback<T> {

        void success(@Nullable final T result);
        void failure(@NonNull final WebServiceException e);

    }

}
