package me.shkschneider.skeleton.network

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.annotation.Size
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.extensions.isNotNull
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.SkHide
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class WebService {

    private val TYPE = JsonElement::class.java
    private val TIMEOUT_CONNECT = 15000
    private val TIMEOUT_READ = 60000

    private val method: WebService.Method
    private val url: String
    private var type: Class<*> = TYPE
    private var headers: Map<String, String>? = null
    private var body: Map<String, String>? = null
    private var callback: Callback<Any>? = null

    constructor(method: WebService.Method, url: String) {
        this.method = method
        this.url = url
    }

    @SkHide
    fun url(): String? {
        return url
    }

    fun headers(headers: Map<String, String>?): WebService {
        this.headers = headers
        return this
    }

    @SkHide
    fun headers(): Map<String, String>? {
        return headers
    }

    fun body(body: Map<String, String>?): WebService {
        this.body = body
        return this
    }

    @SkHide
    fun body(): Map<String, String>? {
        return body
    }

    @Deprecated("WebService.callback(Class<*>, WebService.Callback<Any>)")
    fun callback(callback: WebService.Callback<Any>?): WebService {
        this.callback = callback
        return this
    }

    fun callback(type: Class<*>, callback: WebService.Callback<Any>?): WebService {
        this.type = type
        this.callback = callback
        return this
    }

    @SkHide
    fun callback(): Callback<*>? {
        return callback
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
            return when (this) {
                POST, PUT, DELETE // Although SHOULD be ignored
                -> true
                else -> false
            }
        }

    }

    @SuppressLint("StaticFieldLeak") // FIXME
    private inner class Task : AsyncTask<Void, Void, Any>() {

        override fun doInBackground(@Size(0) vararg voids: Void): Any {
            var httpURLConnection: HttpURLConnection? = null
            try {
                val url = URL(url)
                httpURLConnection = url.openConnection() as HttpURLConnection
                with (httpURLConnection) {
                    connectTimeout = TIMEOUT_CONNECT
                    readTimeout = TIMEOUT_READ
                    useCaches = false
                    doInput = true
                    headers?.forEach {
                        setRequestProperty(it.key, it.value)
                    }
                    requestMethod = method.name
                    doOutput = method.allowsBody()
                    if (! doOutput && body != null) {
                        return WebServiceException(WebServiceException.INTERNAL_ERROR, "Body not allowed")
                    }
                    body?.let {
                        setRequestProperty("Content-Type", MimeTypeHelper.APPLICATION_FORMURLENCODED)
                        val dataOutputStream = DataOutputStream(outputStream)
                        var params = ""
                        it.keys.forEach {
                            params += it + "=" + UrlHelper.encode(it)
                        }
                        val bufferedWriter = BufferedWriter(OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8))
                        bufferedWriter.write(params)
                        bufferedWriter.flush()
                        bufferedWriter.close()
                        dataOutputStream.close()
                    }
                    LogHelper.debug("=> " + method.name + " " + url + " " + (if (headers != null) headers!!.toString() else "{}") + " " + if (body != null) body!!.toString() else "{}")
                    LogHelper.debug("<= $responseCode $responseMessage $url")
                    if (errorStream.isNotNull()) {
                        val body = FileHelper.readString(errorStream)
                        if (! body.isNullOrEmpty()) {
                            LogHelper.verbose("<- " + body!!)
                            return WebServiceException(responseCode, body)
                        }
                        return WebServiceException(responseCode, responseMessage)
                    }
                    val body = FileHelper.readString(inputStream)
                    LogHelper.verbose("<- " + body!!)
                    return Gson().fromJson(body, type) ?: WebServiceException(responseCode, responseMessage)
                }
            } catch (e: JsonSyntaxException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, JsonSyntaxException::class.java.simpleName)
            } catch (e: MalformedURLException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, MalformedURLException::class.java.simpleName)
            } catch (e: IOException) {
                LogHelper.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, IOException::class.java.simpleName)
            } finally {
                httpURLConnection?.disconnect()
            }
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            callback?.let {
                when (result) {
                    is WebServiceException -> it.failure((result as WebServiceException?)!!)
                    null -> it.success(null)
                    type::class.java == result::class.java -> it.success(result)
                    else -> it.failure(WebServiceException(WebServiceException.INTERNAL_ERROR,
                            result::class.java.simpleName + " != " + type::class.java.simpleName))
                }
            }
        }

    }

    interface Callback<in T> {

        fun success(result: T?)
        fun failure(e: WebServiceException)

    }

}
