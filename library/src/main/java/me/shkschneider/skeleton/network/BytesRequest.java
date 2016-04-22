package me.shkschneider.skeleton.network;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import me.shkschneider.skeleton.data.MimeTypeHelper;

// <https://github.com/DWorkS/VolleyPlus>
public class BytesRequest extends JsonRequest<byte[]> {

    public BytesRequest(final int method, @NonNull final String url, @NonNull final String requestBody, final Response.Listener<byte[]> listener, final Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public BytesRequest(@NonNull final String url, final Response.Listener<byte[]> listener, final Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    public BytesRequest(final int method, @NonNull final String url, final Response.Listener<byte[]> listener, final Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(@NonNull final NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() {
        return MimeTypeHelper.APPLICATION_OCTETSTREAM;
    }

}
