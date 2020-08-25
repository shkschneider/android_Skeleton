package me.shkschneider.skeleton.android.network

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

abstract class BaseWebService(val gson: Gson) {

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

    inline fun <reified T : Any> deserialize(content: String): T? =
            Deserializer(gson, T::class).deserialize(content)

    class Deserializer<T : Any>(private val gson: Gson, private val klass: KClass<T>) : ResponseDeserializable<T> {

        override fun deserialize(content: String): T? =
            tryOrNull {
                gson.fromJson(content, klass.java)
            }

    }

}
