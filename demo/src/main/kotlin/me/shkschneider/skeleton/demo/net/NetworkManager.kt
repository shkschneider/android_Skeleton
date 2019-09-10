package me.shkschneider.skeleton.demo.net

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import me.shkschneider.skeleton.datax.MemoryCache
import me.shkschneider.skeleton.helperx.log.Logger
import me.shkschneider.skeleton.networkx.WebService
import me.shkschneider.skeleton.networkx.WebServiceFailure
import me.shkschneider.skeleton.networkx.WebServiceSuccess
import java.io.Serializable
import java.nio.charset.Charset

object NetworkManager {

    private val proxy by lazy {
        WebService(GsonBuilder().setFieldNamingStrategy { f -> f.name.toLowerCase() }.create())
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
                    cache.get(request.url.toString()) // nullable
                }
                next(request, response)
            }
        }
    }

    fun ip(success: WebServiceSuccess<String>, failure: WebServiceFailure? = null): Request =
            proxy.get("https://ipecho.net/plain", success, failure)

    fun userAgent(success: WebServiceSuccess<HttpBinUserAgent>, failure: WebServiceFailure? = null): Request =
            proxy.get("https://httpbin.org/user-agent", success, failure).cache(success)
                    .authenticate("john", "doe")
                    .body("42")

    // If coming from cache, response.isSuccessful will be false
    private inline fun <reified T : Any> Request.cache(crossinline success: WebServiceSuccess<T>): Request {
        response { request, response, result ->
            val key = request.url.toString()
            result.fold({ data ->
                cache.put(key, data)
                Logger.debug("Caching as '$key'")
            }, {
                cache.get(key)?.let { byteArray ->
                    val data = (byteArray as ByteArray).toString(Charset.defaultCharset())
                    proxy.deserialize<T>(data)?.let { t ->
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
    ) : Serializable // TODO @Parcelize once stable

}