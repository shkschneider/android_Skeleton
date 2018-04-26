package me.shkschneider.skeleton.network

import android.graphics.Bitmap
import android.support.v4.util.LruCache

import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger

// <https://developer.android.com/training/volley/requestqueue.html>
object Proxy {

    private val imageLoader: ImageLoader

    init {
        imageLoader = ImageLoader(Volley.newRequestQueue(ContextHelper.applicationContext()), object : ImageLoader.ImageCache {
            private val CACHE = LruCache<String, Bitmap>(42)
            override fun getBitmap(url: String): Bitmap? {
                try {
                    return CACHE.get(url)
                } catch (e: IllegalStateException) {
                    Logger.wtf(e)
                    return null
                }
            }
            override fun putBitmap(url: String, bitmap: Bitmap) {
                CACHE.put(url, bitmap)
            }
        })
    }

    @Deprecated("Not implemented.")
    fun options(url: String): WebService {
        throw NotImplementedError(WebService.Method.OPTIONS.name)
    }

    fun get(url: String): WebService {
        return WebService(WebService.Method.GET, url)
    }

    @Deprecated("Not implemented.")
    fun head(url: String): WebService {
        throw NotImplementedError(WebService.Method.HEAD.name)
    }

    fun post(url: String): WebService {
        return WebService(WebService.Method.POST, url)
    }

    fun put(url: String): WebService {
        return WebService(WebService.Method.PUT, url)
    }

    fun delete(url: String): WebService {
        return WebService(WebService.Method.DELETE, url)
    }

    @Deprecated("Not implemented.")
    fun trace(url: String): WebService {
        throw NotImplementedError(WebService.Method.TRACE.name)
    }

    @Deprecated("Not implemented.")
    fun connect(url: String): WebService {
        throw NotImplementedError(WebService.Method.CONNECT.name)
    }

    fun imageLoader(): ImageLoader {
        return imageLoader
    }

}
