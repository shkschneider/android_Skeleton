package me.shkschneider.skeleton.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest

import me.shkschneider.skeleton.data.MimeTypeHelper

// <https://github.com/DWorkS/VolleyPlus>
class BytesRequest(method: Int = Method.GET,
                   url: String,
                   listener: Response.Listener<ByteArray>? = null,
                   errorListener: Response.ErrorListener? = null
) : JsonRequest<ByteArray>(
        method, url, null, listener, errorListener
) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getBodyContentType(): String {
        return MimeTypeHelper.APPLICATION_OCTETSTREAM
    }

}
