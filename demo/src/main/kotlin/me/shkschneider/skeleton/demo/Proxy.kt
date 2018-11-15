package me.shkschneider.skeleton.demo

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.annotations.SerializedName
import me.shkschneider.skeleton.datax.MemoryCache
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.networkx.WebService
import java.io.Serializable
import java.nio.charset.Charset

object Proxy {

    private val proxy by lazy {
        WebService()
    }
    // cache.clear()
    val cache by lazy {
        MemoryCache<String, Any>()
    }

    init {
        FuelManager.instance.addRequestInterceptor { next: (Request) -> Request ->
            { request: Request ->
                next(request)
            }
        }
        FuelManager.instance.addResponseInterceptor { next: (Request, Response) -> Response ->
            { request: Request, response: Response ->
                if (response.isSuccessful) {
                    cache.put(request.url.toString(), response.data)
                } else {
                    cache.get(request.url.toString())
                }
                next(request, response)
            }
        }
    }

    fun ip(success: (Request, Response, String) -> Unit, failure: ((Request, Response, Exception) -> Unit)? = null): Request =
            proxy.get("https://ipecho.net/plain", success, failure)

    fun userAgent(success: (Request, Response, HttpBinUserAgent) -> Unit, failure: ((Request, Response, Exception) -> Unit)? = null): Request =
            proxy.get("https://httpbin.org/user-agent", success, failure).cache(success)
                    .authenticate("john", "doe")
                    .body("42")

    // If coming from cache, response.isSuccessful will be false
    private inline fun <reified T: Any> Request.cache(crossinline success: (Request, Response, T) -> Unit): Request {
        response { request, response, result ->
            val key = request.url.toString()
            result.fold({ data ->
                cache.put(key, data)
                Logger.debug("Caching as '$key'")
            }, {
                cache.get(key)?.let { byteArray ->
                    val data = (byteArray as ByteArray).toString(Charset.defaultCharset())
                    WebService.Deserializer(T::class).deserialize(data)?.let { t ->
                        success(request, response, t)
                    }
                }
            })
        }
        return this
    }

    data class HttpBinUserAgent(
            @SerializedName("user-agent")
            val userAgent: String?
    ) : Serializable

}