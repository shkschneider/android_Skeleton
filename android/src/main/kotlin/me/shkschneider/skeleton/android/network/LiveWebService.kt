package me.shkschneider.skeleton.android.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.livedata.liveDataResponseObject
import com.google.gson.Gson
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.core.kotlinx.Quad

typealias LiveWebServiceResponse<T> = Quad<Request, Response, T?, Exception?>

@Suppress("DEPRECATION")
open class LiveWebService(gson: Gson = Gson()) : BaseWebService(gson) {

    inline fun <reified T : Any> get(url: String, owner: LifecycleOwner, observer: Observer<LiveWebServiceResponse<T>>) =
            request(Fuel.get(url), owner, observer)

    inline fun <reified T : Any> head(url: String, owner: LifecycleOwner, observer: Observer<LiveWebServiceResponse<T>>) =
            request(Fuel.head(url), owner, observer)

    inline fun <reified T : Any> post(url: String, owner: LifecycleOwner, observer: Observer<LiveWebServiceResponse<T>>) =
            request(Fuel.post(url), owner, observer)

    inline fun <reified T : Any> put(url: String, owner: LifecycleOwner, observer: Observer<LiveWebServiceResponse<T>>) =
            request(Fuel.put(url), owner, observer)

    inline fun <reified T : Any> delete(url: String, owner: LifecycleOwner, observer: Observer<LiveWebServiceResponse<T>>) =
            request(Fuel.delete(url), owner, observer)

    @Deprecated("@hide")
    inline fun <reified T : Any> request(request: Request,
                                         owner: LifecycleOwner,
                                         observer: Observer<LiveWebServiceResponse<T>>
    ) = request.liveDataResponseObject(Deserializer(gson, T::class)).observe(owner, { pair ->
        val fuelError = pair.second.component2()?.also { error ->
            Logger.error("${error.response.statusCode} ${error.response.url}", error.exception)
        }
        observer.onChanged(LiveWebServiceResponse(request, pair.first, pair.second.get(), fuelError?.exception))
    })

}
