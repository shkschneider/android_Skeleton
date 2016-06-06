package me.shkschneider.skeleton.network;

import android.support.annotation.NonNull;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

import me.shkschneider.skeleton.data.CharsetHelper;
import me.shkschneider.skeleton.helper.DateTimeHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.SkHide;

public class MyRequest extends Request<MyResponse> {

    private Map<String, String> mBody;
    private Response.Listener<MyResponse> mListener;
    private int mCacheTimeout = 60 * 60 * 1000;

    public MyRequest(final int method, final String url, final Map<String, String> body, final Response.Listener<MyResponse> listener, final Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mBody = body;
        mListener = listener;
        LogHelper.debug(getMethodName() + " " + url + " " + body);
    }

    public MyRequest(final int method, final String url, final Response.Listener<MyResponse> listener, final Response.ErrorListener errorListener) {
        this(method, url, null, listener, errorListener);
    }

    @Deprecated
    public MyRequest(final int method, final String url, final Response.ErrorListener errorListener) {
        this(method, url, null, null, errorListener);
    }

    @Deprecated
    public MyRequest(final String url, final Response.ErrorListener errorListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, null, null, errorListener);
    }

    @SkHide
    @Override
    protected void deliverResponse(final MyResponse response) {
        if (mListener == null) {
            return;
        }
        LogHelper.verbose(response.getCode() + ": " + response.toString());
        mListener.onResponse(response);
    }

    @SkHide
    @Override
    public void deliverError(final VolleyError error) {
        LogHelper.wtf(error);
        super.deliverError(error);
    }

    @SkHide
    @Override
    protected String getParamsEncoding() {
        return CharsetHelper.UTF8;
    }

    @SkHide
    @Override
    protected Map<String, String> getParams() {
        return mBody;
    }

    public String getMethodName() {
        switch (getMethod()) {
            case Method.DEPRECATED_GET_OR_POST: return "GET/POST";
            case Method.GET: return "GET";
            case Method.POST: return "POST";
            case Method.PUT: return "PUT";
            case Method.DELETE: return "DELETE";
            case Method.HEAD: return "HEAD";
            case Method.OPTIONS: return "OPTIONS";
            case Method.TRACE: return "TRACE";
            case Method.PATCH: return "PATCH";
            // CONNECT not available using Volley
            default: return "?";
        }
    }

    // <http://stackoverflow.com/a/17641225>
    private Priority mPriority;

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    public MyRequest setPriority(@NonNull final Priority priority) {
        mPriority = priority;
        return this;
    }

    // <http://stackoverflow.com/a/21009890>
    private boolean mCached;

    @SkHide
    @Override
    public void addMarker(final String tag) {
        super.addMarker(tag);
        mCached = false;
        if (tag.equals("cache-hit")) { // cache-hit-expired
            mCached = true;
        }
    }

    // <http://stackoverflow.com/a/32022946>
    public MyRequest setCacheTimeout(final int ms) {
        mCacheTimeout = ms;
        return this;
    }

    @SkHide
    @Override
    protected Response<MyResponse> parseNetworkResponse(@NonNull final NetworkResponse networkResponse) {
        Cache.Entry cacheEntry = parseCacheHeaders(networkResponse, mCacheTimeout);
        if (cacheEntry == null) {
            cacheEntry = new Cache.Entry();
        }
        if (! mCached) {
            LogHelper.verbose("networkTime: " + networkResponse.networkTimeMs + "ms");
        }
        else {
            LogHelper.verbose("cached: " + cacheEntry.etag);
        }
        cacheEntry.data = networkResponse.data;
        cacheEntry.ttl = cacheEntry.softTtl = DateTimeHelper.now() + mCacheTimeout;
        if (networkResponse.headers.containsKey("Date")) {
            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers.get("Date"));
        }
        if (networkResponse.headers.containsKey("Last-Modified")) {
            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers.get("Last-Modified"));
        }
        cacheEntry.responseHeaders = networkResponse.headers;
        return Response.success(new MyResponse(networkResponse), cacheEntry);
    }

    // <http://stackoverflow.com/a/16852314>
    private Cache.Entry parseCacheHeaders(final NetworkResponse response, final int timeout) {
        if (timeout <= 0) {
            return HttpHeaderParser.parseCacheHeaders(response);
        }
        final Map<String, String> headers = response.headers;
        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = headers.get("ETag");
        entry.ttl = entry.softTtl = DateTimeHelper.now() + timeout;
        entry.serverDate = (headers.containsKey("Date") ? HttpHeaderParser.parseDateAsEpoch(headers.get("Date")) : 0);
        entry.responseHeaders = headers;
        return entry;
    }

}
