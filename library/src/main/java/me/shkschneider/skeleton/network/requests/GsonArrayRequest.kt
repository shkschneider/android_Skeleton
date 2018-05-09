package me.shkschneider.skeleton.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonArray
import me.shkschneider.skeleton.data.CharsetHelper
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

// <https://android.googlesource.com/platform/frameworks/volley/+/master/src/main/java/com/android/volley/toolbox/JsonArrayRequest.java>
class GsonArrayRequest(method: Int = Method.GET,
                       url: String,
                       listener: Response.Listener<JsonArray>? = null,
                       errorListener: Response.ErrorListener? = null
) : JsonRequest<JsonArray>(
        method, url, null, listener, errorListener
) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<JsonArray> {
        try {
            val string = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers, CharsetHelper.UTF8)))
            val jsonArray = Gson().fromJson(string, JsonArray::class.java)
            return Response.success(jsonArray, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        }
    }

}
