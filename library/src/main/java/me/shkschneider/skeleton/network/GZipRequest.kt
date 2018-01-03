package me.shkschneider.skeleton.network

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.zip.GZIPInputStream

// <https://github.com/DWorkS/VolleyPlus>
class GZipRequest : StringRequest {

    constructor(method: Int, url: String, listener: Response.Listener<String>, errorListener: Response.ErrorListener) : super(method, url, listener, errorListener)

    constructor(url: String, listener: Response.Listener<String>, errorListener: Response.ErrorListener) : super(url, listener, errorListener)

    override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
        try {
            val gzipInputStream = GZIPInputStream(ByteArrayInputStream(response.data))
            // <https://stackoverflow.com/a/41000650>
            val string = gzipInputStream.bufferedReader().use(BufferedReader::readText)
            gzipInputStream.close()
            return Response.success(string, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: IOException) {
            return Response.error(ParseError(e))
        }
    }

}
