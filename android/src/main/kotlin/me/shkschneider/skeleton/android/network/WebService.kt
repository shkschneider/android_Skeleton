package me.shkschneider.skeleton.android.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import me.shkschneider.skeleton.kotlin.log.Logger
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

typealias WebServiceSuccess<T> = (Request, Response, T?) -> Unit
typealias WebServiceFailure = (Request, Response, Throwable) -> Unit

@Suppress("DEPRECATION")
open class WebService(val gson: Gson = Gson()) {

    init {
        if (ApplicationHelper.debuggable) {
            FuelManager.instance.addRequestInterceptor { next: (Request) -> Request ->
                { request: Request ->
                    Logger.info(request.toString())
                    next(request)
                }
            }
            FuelManager.instance.addResponseInterceptor { next: (Request, Response) -> Response ->
                { request: Request, response: Response ->
                    Logger.info(response.toString())
                    next(request, response)
                }
            }
        }
        FuelManager.instance.timeoutInMillisecond = TimeUnit.SECONDS.toMillis(15).toInt()
        FuelManager.instance.timeoutReadInMillisecond = TimeUnit.SECONDS.toMillis(15).toInt()
    }

    inline fun <reified T : Any> get(url: String, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request<T>(Fuel.get(url), success, failure)

    inline fun <reified T : Any> head(url: String, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request<T>(Fuel.head(url), success, failure)

    inline fun <reified T : Any> post(url: String, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request<T>(Fuel.post(url), success, failure)

    inline fun <reified T : Any> put(url: String, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request<T>(Fuel.put(url), success, failure)

    inline fun <reified T : Any> delete(url: String, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request<T>(Fuel.delete(url), success, failure)

    @Deprecated("@hide")
    inline fun <reified T : Any> request(request: Request, crossinline success: WebServiceSuccess<T>, noinline failure: WebServiceFailure? = null): Request =
        request.responseObject(Deserializer(gson, T::class)) { _, response, result: Result<T, FuelError> ->
            result.fold({ data ->
                success(request, response, data)
            }, { fuelError ->
                Logger.error("${fuelError.response.statusCode} ${fuelError.response.url}", fuelError.exception)
                failure?.invoke(request, response, fuelError.exception)
            })
        }

    class Deserializer<T : Any>(private val gson: Gson, private val klass: KClass<T>) : ResponseDeserializable<T> {

        override fun deserialize(content: String): T? =
            tryOrNull {
                gson.fromJson(content, klass.java)
            }

    }

}
