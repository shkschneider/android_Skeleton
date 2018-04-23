package me.shkschneider.skeleton.network

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.annotation.Size
import com.google.gson.*
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.FileHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.java.SkHide
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class WebService {

    private val TIMEOUT_CONNECT = 15000
    private val TIMEOUT_READ = 60000

    private val method: WebService.Method
    private val url: String
    private var headers: Map<String, String>? = null
    private var body: Map<String, String>? = null
    private var callback: Callback? = null

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

    fun callback(callback: WebService.Callback?): WebService {
        this.callback = callback
        return this
    }

    @SkHide
    fun callback(): WebService.Callback? {
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

    @SuppressLint("StaticFieldLeak")
    private inner class Task : AsyncTask<Void, Void, Any?>() {

        override fun doInBackground(@Size(0) vararg voids: Void): Any? {
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
                    body?.let {
                        if (! doOutput) {
                            return WebServiceException(WebServiceException.INTERNAL_ERROR, "Body not allowed")
                        }
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
                    Logger.debug("=> " + method.name + " " + url + " " + (headers?.toString() ?: "{}") + " " + (body?.toString() ?: "{}"))
                    Logger.debug("<= $responseCode $responseMessage $url")
                    return errorStream?.let {
                        FileHelper.readString(errorStream).takeIf {
                            return it?.isNotBlank()
                        } ?.let {
                            Logger.verbose("<- $it")
                            return it
                        } ?: run {
                            return WebServiceException(responseCode, responseMessage)
                        }
                    }
                }
            } catch (e: JsonSyntaxException) {
                Logger.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, JsonSyntaxException::class.java.simpleName)
            } catch (e: MalformedURLException) {
                Logger.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, MalformedURLException::class.java.simpleName)
            } catch (e: IOException) {
                Logger.wtf(e)
                return WebServiceException(WebServiceException.INTERNAL_ERROR, IOException::class.java.simpleName)
            } finally {
                httpURLConnection?.disconnect()
            }
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            when (result) {
                is WebServiceException -> callback?.failure(result)
                else -> callback?.success(result as? String)
            }
        }

    }

    interface Callback {

        fun success(result: String?)
        fun failure(e: WebServiceException)

    }

}
