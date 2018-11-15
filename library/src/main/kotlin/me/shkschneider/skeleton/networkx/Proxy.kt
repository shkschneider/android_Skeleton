package me.shkschneider.skeleton.networkx

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helperx.Logger
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

open class Proxy {

    init {
        if (ApplicationHelper.debuggable()) {
            FuelManager.instance.addRequestInterceptor {
                next: (Request) -> Request -> { request: Request ->
                    Logger.info(request.toString())
                    next(request)
                }
            }
            FuelManager.instance.addResponseInterceptor {
                next: (Request, Response) -> Response -> { request: Request, response: Response ->
                    Logger.info(response.toString())
                    next(request, response)
                }
            }
        }
        FuelManager.instance.timeoutInMillisecond = TimeUnit.SECONDS.toMillis(15).toInt()
        FuelManager.instance.timeoutReadInMillisecond = TimeUnit.SECONDS.toMillis(15).toInt()
    }

    inline fun <reified T:Any> get(url: String,
                                   crossinline success: (Request, Response, T) -> Unit,
                                   noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = Fuel.get(url).apply { unwrap(request, success, failure) }

    inline fun <reified T:Any> head(url: String,
                                    crossinline success: (Request, Response, T) -> Unit,
                                    noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = Fuel.head(url).apply { unwrap(request, success, failure) }

    inline fun <reified T:Any> post(url: String,
                                    crossinline success: (Request, Response, T) -> Unit,
                                    noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = Fuel.post(url).apply { unwrap(request, success, failure) }

    inline fun <reified T:Any> put(url: String,
                                   crossinline success: (Request, Response, T) -> Unit,
                                   noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = Fuel.put(url).apply { unwrap(request, success, failure) }

    inline fun <reified T:Any> delete(url: String,
                                      crossinline success: (Request, Response, T) -> Unit,
                                      noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = Fuel.delete(url).apply { unwrap(request, success, failure) }

    inline fun <reified T:Any> unwrap(
            x: Request,
            crossinline success: (Request, Response, T) -> Unit,
            noinline failure: ((Request, Response, Exception) -> Unit)? = null
    ): Request = x.responseObject(Deserializer(T::class)) { request: Request, response: Response, result ->
        result.fold({ data ->
            success(request, response, data)
        }, { fuelError ->
            Logger.error("${fuelError.response.statusCode} ${fuelError.response.url}", fuelError.exception)
            failure?.invoke(request, response, fuelError.exception)
        })
    }

    class Deserializer<T:Any>(private val klass: KClass<T>): ResponseDeserializable<T> {

        override fun deserialize(content: String): T? = Gson().fromJson(content, klass.java)

    }

}
