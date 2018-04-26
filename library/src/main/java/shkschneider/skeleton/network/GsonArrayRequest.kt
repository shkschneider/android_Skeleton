package me.shkschneider.skeleton.network

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonArray
import me.shkschneider.skeleton.data.CharsetHelper
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

// <https://android.googlesource.com/platform/frameworks/volley/+/master/src/main/java/com/android/volley/toolbox/JsonArrayRequest.java>
class GsonArrayRequest : JsonRequest<JsonArray> {

    constructor(method: Int, url: String, requestBody: String, listener: Response.Listener<JsonArray>, errorListener: Response.ErrorListener) : super(method, url, requestBody, listener, errorListener)

    constructor(url: String, listener: Response.Listener<JsonArray>, errorListener: Response.ErrorListener) : super(Request.Method.GET, url, null, listener, errorListener)

    constructor(method: Int, url: String, listener: Response.Listener<JsonArray>, errorListener: Response.ErrorListener) : super(method, url, null, listener, errorListener)

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
