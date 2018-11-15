package me.shkschneider.skeleton.demo

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Proxy : me.shkschneider.skeleton.networkx.Proxy() {

    fun ip(success: (Request, Response, String) -> Unit, failure: ((Request, Response, Exception) -> Unit)? = null): Request =
            get("https://ipecho.net/plain", success, failure)

    fun userAgent(success: (Request, Response, HttpBinUserAgent) -> Unit, failure: ((Request, Response, Exception) -> Unit)? = null): Request =
            get("https://httpbin.org/user-agent", success, failure)
                    .authenticate("john", "doe")
                    .body("42")

    data class HttpBinUserAgent(
            @SerializedName("user-agent")
            val userAgent: String?
    ) : Serializable

}