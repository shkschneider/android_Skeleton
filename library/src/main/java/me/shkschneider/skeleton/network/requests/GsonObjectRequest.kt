package me.shkschneider.skeleton.network.requests

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.shkschneider.skeleton.data.CharsetHelper
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

// <https://android.googlesource.com/platform/frameworks/volley/+/master/src/main/java/com/android/volley/toolbox/JsonObjectRequest.java>
class GsonObjectRequest(method: Int = Method.GET,
                        url: String,
                        listener: Response.Listener<JsonObject>? = null,
                        errorListener: Response.ErrorListener? = null
) : JsonRequest<JsonObject>(
        method, url, null, listener, errorListener
) {

    override fun parseNetworkResponse(response: NetworkResponse): Response<JsonObject> {
        try {
            val string = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers, CharsetHelper.UTF8)))
            val jsonObject = Gson().fromJson(string, JsonObject::class.java)
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        }
    }

}
