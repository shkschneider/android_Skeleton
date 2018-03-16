package me.shkschneider.skeleton.network

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.helper.DateTimeHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.SkHide
import java.util.concurrent.TimeUnit

class MyRequest : Request<MyResponse> {

    private val listener: Response.Listener<MyResponse>?
    private val body: Map<String, String>?

    constructor(method: Int, url: String, body: Map<String, String>?, listener: Response.Listener<MyResponse>?, errorListener: Response.ErrorListener?) : super(method, url, errorListener) {
        this.listener = listener
        this.body = body
        LogHelper.debug(methodName() + " $url $body")
    }

    constructor(method: Int = Request.Method.DEPRECATED_GET_OR_POST, url: String, listener: Response.Listener<MyResponse>?, errorListener: Response.ErrorListener?) : this(method, url, null, listener, errorListener)

    private var cacheTimeout = TimeUnit.HOURS.toMillis(1).toInt()

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
    private var priority: Request.Priority? = null

    // <http://stackoverflow.com/a/21009890>
    private var cached = false

    @SkHide
    override fun deliverResponse(response: MyResponse) {
        listener?.let {
            LogHelper.verbose(response.code().toString() + ": " + response.toString())
            it.onResponse(response)
        }
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
        return body
    }

    override fun getPriority(): Request.Priority? {
        return priority
    }

    fun setPriority(priority: Request.Priority): MyRequest {
        this.priority = priority
        return this
    }

    @SkHide
    override fun addMarker(tag: String) {
        super.addMarker(tag)
        cached = (tag == "cache-hit") // cache-hit-expired
    }

    // <http://stackoverflow.com/a/32022946>
    fun setCacheTimeout(ms: Int): MyRequest {
        cacheTimeout = ms
        return this
    }

    @SkHide
    override fun parseNetworkResponse(networkResponse: NetworkResponse): Response<MyResponse> {
        var cacheEntry = parseCacheHeaders(networkResponse, cacheTimeout)
        with (cacheEntry) {
            if (!cached) {
                LogHelper.verbose("networkTime: " + networkResponse.networkTimeMs + "ms")
            } else {
                LogHelper.verbose("cached: " + etag)
            }
            data = networkResponse.data
            softTtl = DateTimeHelper.now() + cacheTimeout
            ttl = softTtl
            if (networkResponse.headers.containsKey("Date")) {
                serverDate = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers["Date"])
            }
            if (networkResponse.headers.containsKey("Last-Modified")) {
                lastModified = HttpHeaderParser.parseDateAsEpoch(networkResponse.headers["Last-Modified"])
            }
            responseHeaders = networkResponse.headers
        }
        return Response.success(MyResponse(networkResponse), cacheEntry)
    }

    // <http://stackoverflow.com/a/16852314>
    private fun parseCacheHeaders(response: NetworkResponse, timeout: Int): Cache.Entry {
        if (timeout <= 0) {
            return HttpHeaderParser.parseCacheHeaders(response)
        }
        val headers = response.headers
        val entry = Cache.Entry()
        with (entry) {
            data = response.data
            etag = headers["ETag"]
            softTtl = DateTimeHelper.now() + timeout
            ttl = entry.softTtl
            serverDate = if (headers.containsKey("Date")) HttpHeaderParser.parseDateAsEpoch(headers["Date"]) else 0
            responseHeaders = headers
        }
        return entry
    }

}
