package me.shkschneider.skeleton.network;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;

import me.shkschneider.skeleton.data.CharsetHelper;

// <https://android.googlesource.com/platform/frameworks/volley/+/master/src/main/java/com/android/volley/toolbox/JsonObjectRequest.java>
public class GsonObjectRequest extends JsonRequest<JsonObject> {

    public GsonObjectRequest(final int method, @NonNull final String url, @NonNull final String requestBody, final Response.Listener<JsonObject> listener, final Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public GsonObjectRequest(@NonNull final String url, final Response.Listener<JsonObject> listener, final Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    public GsonObjectRequest(final int method, @NonNull final String url, final Response.Listener<JsonObject> listener, final Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    protected Response<JsonObject> parseNetworkResponse(@NonNull final NetworkResponse response) {
        try {
            final String string = new String(response.data, HttpHeaderParser.parseCharset(response.headers, CharsetHelper.UTF8));
            final JsonObject jsonObject = new Gson().fromJson(string, JsonObject.class);
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (final UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

}
