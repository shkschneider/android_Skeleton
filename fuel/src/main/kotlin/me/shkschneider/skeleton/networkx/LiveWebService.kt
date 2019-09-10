package me.shkschneider.skeleton.networkx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.livedata.liveDataResponseObject
import com.google.gson.Gson
import me.shkschneider.skeleton.helperx.log.Logger
import me.shkschneider.skeleton.kotlinx.Quad

public typealias LiveWebServiceResponse<T> = Quad<Request, Response, T?, Exception?>

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
    ) = request.liveDataResponseObject(Deserializer(gson, T::class)).observe(owner, Observer {
        val fuelError = it.second.component2()?.also {
            Logger.error("${it.response.statusCode} ${it.response.url}", it.exception)
        }
        observer.onChanged(LiveWebServiceResponse(request, it.first, it.second.get(), fuelError?.exception))
    })

}
