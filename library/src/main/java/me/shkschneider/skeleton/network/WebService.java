package me.shkschneider.skeleton.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.data.JsonParser;
import me.shkschneider.skeleton.data.MimeTypeHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.java.StringHelper;

// <http://developer.android.com/reference/java/net/HttpURLConnection.html>
public class WebService extends AsyncTask<Void, Void, Object> {

    private static final int TIMEOUT_CONNECT = 15000;
    private static final int TIMEOUT_READ = 60000;

    @Nullable
    private Activity mActivity;
    private Method mMethod;
    private String mUrl;
    @Nullable
    private Map<String, String> mBody;
    @Nullable
    private Callback mCallback;

    public WebService(@Nullable final Activity activity, @NonNull final Method method, @NonNull final String url, @Nullable final Map<String, String> body, @Nullable final Callback callback) {
        mActivity = activity;
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
            final URL url = new URL(mUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(TIMEOUT_CONNECT);
            httpURLConnection.setReadTimeout(TIMEOUT_READ);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            final String method = mMethod.toString();
            if (method == null) {
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Bad method");
            }
            final boolean allowsBody = mMethod.allowsBody();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(allowsBody);
            if (! allowsBody && mBody != null) {
                return new WebServiceException(WebServiceException.INTERNAL_ERROR, "Body not allowed");
            }
            if (mBody != null) {
                httpURLConnection.setRequestProperty("Content-Type", MimeTypeHelper.APPLICATION_FORMURLENCODED);
                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                String params = "";
                for (final String key : mBody.keySet()) {
                    if (! StringHelper.nullOrEmpty(params)) params += "&";
                    params += key + "=" + UrlHelper.encode(mBody.get(key));
                }
                LogHelper.verbose("STDIN: " + params);
                final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8));
                bufferedWriter.write(params);
                bufferedWriter.flush();
                bufferedWriter.close();
                dataOutputStream.close();
            }

            final int responseCode = httpURLConnection.getResponseCode();
            final String responseMessage = httpURLConnection.getResponseMessage();
            LogHelper.debug("HTTP/" + responseCode + " " + responseMessage);
            final InputStream errorStream = httpURLConnection.getErrorStream();
            if (errorStream != null) {
                final String body = FileHelper.readString(errorStream);
                if (! StringHelper.nullOrEmpty(body)) {
                    LogHelper.verbose("STDERR: " + body);
                    return new WebServiceException(responseCode, body);
                }
                return new WebServiceException(responseCode, responseMessage);
            }
            final InputStream inputStream = httpURLConnection.getInputStream();
            final String body = FileHelper.readString(inputStream);
            LogHelper.verbose("STDOUT: " + body);
            if (StringHelper.nullOrEmpty(body)) {
                return "";
            }
            else {
                final JSONObject jsonObject = JsonParser.parse(body);
                if (jsonObject != null) {
                    return jsonObject;
                }
            }
            return new WebServiceException(responseCode, responseMessage);
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
    protected void onPostExecute(@Nullable final Object object) {
        // Should not happen
        if (object == null) {
            LogHelper.warning("Nothing");
            final WebServiceException webServiceException = new WebServiceException(WebServiceException.INTERNAL_ERROR, "Nothing");
            callback(webServiceException, null);
        }
        // Could happen
        else if ((object instanceof String) && ((String) object).length() == 0) {
            callback(null, null);
        }
        // Could happen
        else if (object instanceof WebServiceException) {
            final WebServiceException webServiceException = (WebServiceException) object;
            callback(webServiceException, null);
        }
        // Should happen
        else if (object instanceof JSONObject) {
            final JSONObject jsonObject = (JSONObject) object;
            callback(null, jsonObject);
        }
        // Should never happen
        else {
            LogHelper.error("Invalid");
            final WebServiceException webServiceException = new WebServiceException(WebServiceException.INTERNAL_ERROR, "Invalid");
            callback(webServiceException, null);
        }
    }

    private void callback(@Nullable final WebServiceException e, @Nullable final JSONObject jsonObject) {
        if (mCallback == null) return;
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCallback.webServiceCallback(e, jsonObject);
                }
            });
        }
        else {
            mCallback.webServiceCallback(e, jsonObject);
        }
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
            switch (this) {
                case GET:
                    return "GET";
                case POST:
                    return "POST";
                case PUT:
                    return "PUT";
                case DELETE:
                    return "DELETE";
                default:
                    return null;
            }
        }

    }

    public interface Callback {

        void webServiceCallback(@Nullable final WebServiceException e, @Nullable final JSONObject jsonObject);

    }

}
