package me.shkschneider.skeleton.network

import android.os.AsyncTask
import android.support.annotation.Size
import android.text.TextUtils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException

import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.ClassHelper
import me.shkschneider.skeleton.java.SkHide

class WebService {

    private val _TYPE = JsonElement::class.java

    private val _method: WebService.Method
    private val _url: String
    private var _type: Class<*> = _TYPE
    private var _headers: Map<String, String>? = null
    private var _body: Map<String, String>? = null
    private var _callback: Callback<Any>? = null

    constructor(method: WebService.Method, url: String) {
        _method = method
        _url = url
    }

    @SkHide
    fun url(): String? {
        return _url
    }

    fun headers(headers: Map<String, String>?): WebService {
        _headers = headers
        return this
    }

    @SkHide
    fun headers(): Map<String, String>? {
        return _headers
    }

    fun body(body: Map<String, String>?): WebService {
        _body = body
        return this
    }

    @SkHide
    fun body(): Map<String, String>? {
        return _body
    }

    @Deprecated("WebService.callback(Class<*>, WebService.Callback<Any>)")
    fun callback(callback: WebService.Callback<Any>?): WebService {
        _callback = callback
        return this
    }

    fun callback(type: Class<*>, callback: WebService.Callback<Any>?): WebService {
        _type = type
        _callback = callback
        return this
    }

    @SkHide
    fun callback(): Callback<*>? {
        return _callback
    }

    fun run() {
        Task().execute()
    }

    // <https://www.ietf.org/rfc/rfc2616.txt>
    enum class Method {

        OPTIONS,
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        TRACE,
        CONNECT;

        fun allowsBody(): Boolean {
            when (this) {
                POST, PUT, DELETE // Although SHOULD be ignored
                -> return true
                else -> return false
            }
        }

    }

    private inner class Task : AsyncTask<Void, Void, Any>() {

        private val _TIMEOUT_CONNECT = 15000
        private val _TIMEOUT_READ = 60000

        override fun doInBackground(@Size(0) vararg voids: Void): Any {
            var httpURLConnection: HttpURLConnection? = null
            try {
                val url = URL(_url)
                httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.connectTimeout = _TIMEOUT_CONNECT
                httpURLConnection.readTimeout = _TIMEOUT_READ
                httpURLConnection.useCaches = false
                httpURLConnection.doInput = true
                if (_headers != null) {
                    for ((key, value) in _headers!!) {
                        httpURLConnection.setRequestProperty(key, value)
                    }
                }
                val allowsBody = _method.allowsBody()
                httpURLConnection.requestMethod = _method.name
                httpURLConnection.doOutput = allowsBody
                if (!allowsBody && _body != null) {
                    return WebServiceException(WebServiceException.INTERNAL_ERROR, "Body not allowed")
                }
                if (_body != null) {
                    httpURLConnection.setRequestProperty("Content-Type", MimeTypeHelper.APPLICATION_FORMURLENCODED)
                    val dataOutputStream = DataOutputStream(httpURLConnection.outputStream)
                    var params = ""
                    for (key in _body!!.keys) {
                        if (!TextUtils.isEmpty(params)) params += "&"
                        params += key + "=" + UrlHelper.encode(_body!![key]!!)
                    }
                    val bufferedWriter = BufferedWriter(OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8))
                    bufferedWriter.write(params)
                    bufferedWriter.flush()
                    bufferedWriter.close()
                    dataOutputStream.close()
                }
                LogHelper.debug("=> " + _method.name + " " + url + " " + (if (_headers != null) _headers!!.toString() else "{}") + " " + if (_body != null) _body!!.toString() else "{}")
                val responseCode = httpURLConnection.responseCode
                val responseMessage = httpURLConnection.responseMessage
                LogHelper.debug("<= $responseCode $responseMessage $url")
                val errorStream = httpURLConnection.errorStream
                if (errorStream != null) {
                    val body = FileHelper.readString(errorStream)
                    if (!TextUtils.isEmpty(body)) {
                        LogHelper.verbose("<- " + body!!)
                        return WebServiceException(responseCode, body)
                    }
                    return WebServiceException(responseCode, responseMessage)
                }
                val inputStream = httpURLConnection.inputStream
                val body = FileHelper.readString(inputStream)
                LogHelper.verbose("<- " + body!!)
                if (TextUtils.isEmpty(body)) {
                    return ""
                } else {
                    val result = Gson().fromJson(body, _type)
                    if (result != null) {
                        return result
                    }
                }
                return WebServiceException(responseCode, responseMessage)
            } catch (e: JsonSyntaxException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.javaClass))
            } catch (e: MalformedURLException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.javaClass))
            } catch (e: IOException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, ClassHelper.simpleName(e.javaClass))
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect()
                }
            }
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            when (result) {
                is WebServiceException -> _callback!!.failure((result as WebServiceException?)!!)
                null -> _callback!!.success(null)
                _type.javaClass == result.javaClass -> _callback!!.success(result)
                else -> _callback!!.failure(WebServiceException(WebServiceException.INTERNAL_ERROR,
                        ClassHelper.simpleName(result.javaClass) + " != " + ClassHelper.simpleName(_type)))
            }
        }

    }

    interface Callback<in T> {

        fun success(result: T?)
        fun failure(e: WebServiceException)

    }

}
