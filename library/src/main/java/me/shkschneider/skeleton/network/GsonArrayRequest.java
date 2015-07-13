package me.shkschneider.skeleton.network;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

// <https://android.googlesource.com/platform/frameworks/volley/+/master/src/main/java/com/android/volley/toolbox/JsonArrayRequest.java>
public class GsonArrayRequest extends JsonRequest<JsonArray> {

    public GsonArrayRequest(final int method, @NonNull final String url, @NonNull final String requestBody, final Response.Listener<JsonArray> listener, final Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public GsonArrayRequest(@NonNull final String url, final Response.Listener<JsonArray> listener, final Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    public GsonArrayRequest(final int method, @NonNull final String url, final Response.Listener<JsonArray> listener, final Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    protected Response<JsonArray> parseNetworkResponse(final NetworkResponse response) {
        try {
            final String string = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
            final JsonArray jsonArray = new Gson().fromJson(string, JsonArray.class);
            return Response.success(jsonArray, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (final Exception e) {
            return Response.error(new ParseError(e));
        }
    }

}
