package me.shkschneider.skeleton.network.requests

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import kotlin.reflect.KClass

class ApiRequest<T:Any>(
        method: Int = Method.GET,
        url: String,
        private val klass: KClass<T>,
        private val headers: MutableMap<String, String>? = null,
        private val body: MutableMap<String, String>? = null,
        private val listener: Response.Listener<T>?,
        errorListener: Response.ErrorListener? = null,
        private val retryPolicy: RetryPolicy? = null
) : Request<T>(method, url, errorListener) {

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }

    override fun getParams(): MutableMap<String, String> {
        return body ?: super.getParams()
    }

    override fun getRetryPolicy(): RetryPolicy {
        return retryPolicy ?: super.getRetryPolicy()
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T>? {
        try {
            val s = String(response?.data ?: ByteArray(0))
            val obj = Gson().fromJson<T>(s, klass.java)
            return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            return Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: T) {
        listener?.onResponse(response)
    }

}
