package me.shkschneider.skeleton.network;

import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

import me.shkschneider.skeleton.helper.LogHelper;

public class MyResponse extends NetworkResponse {

    public MyResponse(final NetworkResponse networkResponse) {
        super(networkResponse.statusCode, networkResponse.data, networkResponse.headers, networkResponse.notModified, networkResponse.networkTimeMs);
    }

    @Deprecated
    public MyResponse(final int statusCode, final byte[] data, final Map<String, String> headers, final boolean notModified, final long networkTimeMs) {
        super(statusCode, data, headers, notModified, networkTimeMs);
    }

    @Deprecated
    public MyResponse(final int statusCode, final byte[] data, final Map<String, String> headers, final boolean notModified) {
        super(statusCode, data, headers, notModified);
    }

    @Deprecated
    public MyResponse(final byte[] data, Map<String, String> headers) {
        super(data, headers);
    }

    @Deprecated
    public MyResponse(final byte[] data) {
        super(data);
    }

    public int getCode() {
        // HttpURLConnection.HTTP_...
        return statusCode;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        try {
            return new String(data, HttpHeaderParser.parseCharset(headers));
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public Status getStatus() {
        if (statusCode >= 500) {
            return Status.SERVER_ERROR;
        }
        else if (statusCode >= 400) {
            return Status.CLIENT_ERROR;
        }
        else if (statusCode >= 300) {
            return Status.REDIRECTION;
        }
        else if (statusCode >= 200) {
            return Status.SUCCESS;
        }
        else if (statusCode >= 100) {
            return Status.INFORMATIONAL;
        }
        return Status.UNKNOWN;
    }

    public enum Status {

        INFORMATIONAL,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNKNOWN

    }

}
