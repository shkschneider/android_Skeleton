package me.shkschneider.skeleton.network

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.annotation.IntRange
import androidx.annotation.Size
import com.google.gson.JsonSyntaxException
import me.shkschneider.skeleton.data.CharsetHelper
import me.shkschneider.skeleton.data.MimeTypeHelper
import me.shkschneider.skeleton.data.StreamHelper
import me.shkschneider.skeleton.helper.Logger
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

// You should have a look at Retrofit or FastAndroidNetworking ;)
open class WebService(
        private val method: WebService.Method,
        private val url: String
) {

    private var headers: Map<String, String>? = null
    private var body: Map<String, String>? = null
    private var callback: Callback? = null

    fun url(): String? {
        return url
    }

    fun headers(headers: Map<String, String>?): WebService {
        this.headers = headers
        return this
    }

    fun headers(): Map<String, String>? {
        return headers
    }

    fun body(body: Map<String, String>?): WebService {
        this.body = body
        return this
    }

    fun body(): Map<String, String>? {
        return body
    }

    fun callback(callback: WebService.Callback?): WebService {
        this.callback = callback
        return this
    }

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
                    headers?.forEach { entry ->
                        setRequestProperty(entry.key, entry.value)
                    }
                    requestMethod = method.name
                    doOutput = method.allowsBody().also {
                        body?.let { body ->
                            setRequestProperty("Content-Type", MimeTypeHelper.APPLICATION_FORMURLENCODED)
                            val dataOutputStream = DataOutputStream(outputStream)
                            var params = ""
                            body.keys.forEach { key ->
                                params += key + "=" + UrlHelper.encode(key)
                            }
                            val bufferedWriter = BufferedWriter(OutputStreamWriter(dataOutputStream, CharsetHelper.UTF8))
                            bufferedWriter.write(params)
                            bufferedWriter.flush()
                            bufferedWriter.close()
                            dataOutputStream.close()
                        }
                    }
                    Logger.debug("=> " + method.name + " " + url + " " + (headers?.toString() ?: "{}") + " " + (body?.toString() ?: "{}"))
                    Logger.debug("<= $responseCode $responseMessage $url")
                    errorStream?.let { errorStream ->
                        return Response(responseCode, StreamHelper.read(errorStream) ?: responseMessage)
                    } ?: StreamHelper.read(inputStream)?.let { response ->
                        Logger.verbose("<- $response")
                        return Response(responseCode, response)
                    }
                    return Response(responseCode, responseMessage)
                }
            } catch (e: JsonSyntaxException) {
                Logger.wtf(e)
                return Error(INTERNAL_ERROR, JsonSyntaxException::class.java.simpleName)
            } catch (e: MalformedURLException) {
                Logger.wtf(e)
                return Error(INTERNAL_ERROR, MalformedURLException::class.java.simpleName)
            } catch (e: IOException) {
                Logger.wtf(e)
                return Error(INTERNAL_ERROR, IOException::class.java.simpleName)
            } finally {
                httpURLConnection?.disconnect()
            }
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            when (result) {
                is Response -> callback?.success(result)
                is Error -> callback?.failure(result)
                else -> throw UnsupportedOperationException("Result was not a Response nor an Exception!")
            }
        }

    }

    class Response(
            @IntRange(from = 0, to = INTERNAL_ERROR.toLong())
            val code: Int,
            val message: String? = null
    ) {

        override fun toString(): String {
            return String.format(Locale.getDefault(), "%d %s", code, message)
        }

    }

    class Error(
            @IntRange(from = 0, to = INTERNAL_ERROR.toLong())
            val code: Int,
            message: String? = null
    ) : Exception(message, null) {

        override fun toString(): String {
            return String.format(Locale.getDefault(), "%d %s", code, message)
        }

    }

    companion object {

        const val INTERNAL_ERROR = 6_6_6
        val TIMEOUT_CONNECT = TimeUnit.SECONDS.toMillis(15).toInt()
        val TIMEOUT_READ = TimeUnit.MINUTES.toMillis(1).toInt()

    }

    interface Callback {

        fun success(result: Response?)
        fun failure(e: Error)

    }

}
