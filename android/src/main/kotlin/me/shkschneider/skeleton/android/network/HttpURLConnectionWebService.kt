package me.shkschneider.skeleton.android.network

import android.os.AsyncTask
import androidx.annotation.IntRange
import androidx.annotation.Size
import me.shkschneider.skeleton.kotlin.data.Charsets
import me.shkschneider.skeleton.kotlin.data.MimeTypes
import me.shkschneider.skeleton.kotlin.data.StreamHelper
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.core.network.UrlHelper
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Locale
import java.util.concurrent.TimeUnit

const val INTERNAL_ERROR = 6_6_6
private val TIMEOUT_CONNECT = TimeUnit.SECONDS.toMillis(15).toInt()
private val TIMEOUT_READ = TimeUnit.MINUTES.toMillis(1).toInt()

@Deprecated("You should have a look at Fuel, Retrofit or FastAndroidNetworking")
open class HttpURLConnectionWebService(
        private val method: Method,
        private val url: String
) {

    private var headers: Map<String, String>? = null
    private var body: Map<String, String>? = null
    private var onSuccess: ((Response?) -> (Unit))? = null
    private var onError: ((Error) -> (Unit))? = null

    fun url(): String? {
        return url
    }

    @Suppress("DEPRECATION")
    fun headers(headers: Map<String, String>?): HttpURLConnectionWebService {
        this.headers = headers
        return this
    }

    fun headers(): Map<String, String>? {
        return headers
    }

    @Suppress("DEPRECATION")
    fun body(body: Map<String, String>?): HttpURLConnectionWebService {
        this.body = body
        return this
    }

    fun body(): Map<String, String>? {
        return body
    }

    @Suppress("DEPRECATION")
    fun onSuccess(onSuccess: ((Response?) -> (Unit))?): HttpURLConnectionWebService {
        this.onSuccess = onSuccess
        return this
    }

    @Suppress("DEPRECATION")
    fun onError(onError: ((Error) -> (Unit))?): HttpURLConnectionWebService {
        this.onError = onError
        return this
    }

    fun run() {
        Task().execute()
    }

    // <https://www.ietf.org/rfc/rfc2616.txt>
    sealed class Method(val name: String, val allowsBody: Boolean?) {

        object Options : Method("OPTIONS", false)
        object Get : Method("GET", false)
        object Head : Method("HEAD", false)
        object Post : Method("POST", true)
        object Put : Method("PUT", true)
        object Delete : Method("DELETE", null)
        object Trace : Method("TRACE", false)
        object Connect : Method("CONNECT", false)

    }

    private inner class Task : AsyncTask<Void, Void, Any?>() {

        override fun doInBackground(@Size(0) vararg voids: Void): Any? {
            var httpURLConnection: HttpURLConnection? = null
            try {
                httpURLConnection = (URL(url).openConnection() as HttpURLConnection)
                with(httpURLConnection) {
                    connectTimeout = TIMEOUT_CONNECT
                    readTimeout = TIMEOUT_READ
                    useCaches = false
                    doInput = true
                    headers?.forEach { entry ->
                        setRequestProperty(entry.key, entry.value)
                    }
                    requestMethod = method.name
                    doOutput = method.allowsBody == true
                    if (doOutput) {
                        body?.let { body ->
                            setRequestProperty("Content-Type", MimeTypes.APPLICATION_FORMURLENCODED)
                            val dataOutputStream = DataOutputStream(outputStream)
                            var params = ""
                            body.keys.forEach { key ->
                                params += key + "=" + UrlHelper.encode(key)
                            }
                            val bufferedWriter = BufferedWriter(OutputStreamWriter(dataOutputStream, Charsets.UTF8))
                            bufferedWriter.write(params)
                            bufferedWriter.flush()
                            bufferedWriter.close()
                            dataOutputStream.close()
                        }
                    }
                    Logger.debug("=> " + method.name + " " + url + " " + (headers?.toString() ?: "{}") + " " + (body?.toString() ?: "{}"))
                    Logger.debug("<= $responseCode $responseMessage $url")
                    errorStream?.let { errorStream ->
                        return Response(responseCode, StreamHelper.read(errorStream)
                                ?: responseMessage)
                    } ?: StreamHelper.read(inputStream)?.let { response ->
                        Logger.verbose("<- $response")
                        return Response(responseCode, response)
                    }
                    return Response(responseCode, responseMessage)
                }
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
                is Response -> onSuccess?.invoke(result)
                is Error -> onError?.invoke(result)
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

}
