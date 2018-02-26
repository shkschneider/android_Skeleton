package me.shkschneider.skeleton.network

import com.android.volley.Cache
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser

import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.helper.DateTimeHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.SkHide
import java.util.concurrent.TimeUnit

class MyRequest : Request<MyResponse> {

    private val _listener: Response.Listener<MyResponse>?
    private val _body: Map<String, String>?

    constructor(method: Int, url: String, body: Map<String, String>?, listener: Response.Listener<MyResponse>?, errorListener: Response.ErrorListener?) : super(method, url, errorListener) {
        this._listener = listener
        this._body = body
        LogHelper.debug(methodName() + " $url $body")
    }

    constructor(method: Int = Request.Method.DEPRECATED_GET_OR_POST, url: String, listener: Response.Listener<MyResponse>?, errorListener: Response.ErrorListener?) : this(method, url, null, listener, errorListener)

    private var _cacheTimeout: Int = TimeUnit.HOURS.toMillis(1).toInt()

    // CONNECT not available using Volley
    fun methodName(): String {
        return when (method) {
            Request.Method.DEPRECATED_GET_OR_POST -> "GET/POST"
            Request.Method.GET -> "GET"
            Request.Method.POST -> "POST"
            Request.Method.PUT -> "PUT"
            Request.Method.DELETE -> "DELETE"
            Request.Method.HEAD -> "HEAD"
            Request.Method.OPTIONS -> "OPTIONS"
            Request.Method.TRACE -> "TRACE"
            Request.Method.PATCH -> "PATCH"
            else -> "?"
        }
    }

    // <http://stackoverflow.com/a/17641225>
    private var _priority: Request.Priority? = null

    // <http://stackoverflow.com/a/21009890>
    private var _cached: Boolean = false

    @SkHide
    override fun deliverResponse(response: MyResponse) {
        if (_listener == null) {
            return
        }
        LogHelper.verbose(response.code().toString() + ": " + response.toString())
        _listener.onResponse(response)
    }

    @SkHide
    override fun deliverError(error: VolleyError) {
        LogHelper.wtf(error)
        super.deliverError(error)
    }

    @SkHide
    override fun getParamsEncoding(): String {
        return CharsetHelper.UTF8
    }

    @SkHide
    override fun getParams(): Map<String, String>? {
        return _body
    }

    override fun getPriority(): Request.Priority? {
        return _priority
    }

    fun setPriority(priority: Request.Priority): MyRequest {
        this._priority = priority
        return this
    }

    @SkHide
    override fun addMarker(tag: String) {
        super.addMarker(tag)
        _cached = tag == "cache-hit" // cache-hit-expired
    }

    // <http://stackoverflow.com/a/32022946>
    fun setCacheTimeout(ms: Int): MyRequest {
        _cacheTimeout = ms
        return this
    }

    @SkHide
    override fun parseNetworkResponse(networkResponse: NetworkResponse): Response<MyResponse> {
        var cacheEntry: Cache.Entry? = parseCacheHeaders(networkResponse, _cacheTimeout)
        if (cacheEntry == null) {
            cacheEntry = Cache.Entry()
        }
        if (!_cached) {
            LogHelper.verbose("networkTime: " + networkResponse.networkTimeMs + "ms")
        } else {
            LogHelper.verbose("_cached: " + cacheEntry.etag)
        }
        cacheEntry.data = networkResponse.data
        cacheEntry.softTtl = DateTimeHelper.now() + _cacheTimeout
        cacheEntry.ttl = cacheEntry.softTtl
        if (networkResponse.headers.containsKey("Date")) {
            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers["Date"])
        }
        if (networkResponse.headers.containsKey("Last-Modified")) {
            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers["Last-Modified"])
        }
        cacheEntry.responseHeaders = networkResponse.headers
        return Response.success(MyResponse(networkResponse), cacheEntry)
    }

    // <http://stackoverflow.com/a/16852314>
    private fun parseCacheHeaders(response: NetworkResponse, timeout: Int): Cache.Entry {
        if (timeout <= 0) {
            return HttpHeaderParser.parseCacheHeaders(response)
        }
        val headers = response.headers
        val entry = Cache.Entry()
        entry.data = response.data
        entry.etag = headers["ETag"]
        entry.softTtl = DateTimeHelper.now() + timeout
        entry.ttl = entry.softTtl
        entry.serverDate = if (headers.containsKey("Date")) HttpHeaderParser.parseDateAsEpoch(headers["Date"]) else 0
        entry.responseHeaders = headers
        return entry
    }

}
