package me.shkschneider.skeleton.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest

import me.shkschneider.skeleton.data.MimeTypeHelper

// <https://github.com/DWorkS/VolleyPlus>
class BytesRequest : JsonRequest<ByteArray> {

    constructor(method: Int, url: String, requestBody: String, listener: Response.Listener<ByteArray>, errorListener: Response.ErrorListener) : super(method, url, requestBody, listener, errorListener)

    constructor(url: String, listener: Response.Listener<ByteArray>, errorListener: Response.ErrorListener) : super(Request.Method.GET, url, null, listener, errorListener)

    constructor(method: Int, url: String, listener: Response.Listener<ByteArray>, errorListener: Response.ErrorListener) : super(method, url, null, listener, errorListener)

    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getBodyContentType(): String {
        return MimeTypeHelper.APPLICATION_OCTETSTREAM
    }

}
