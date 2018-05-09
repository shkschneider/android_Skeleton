package me.shkschneider.skeleton.network

import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*

import me.shkschneider.skeleton.helper.ContextHelper

// <https://developer.android.com/training/volley/requestqueue.html>
object Proxy {

    // Volley

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(ContextHelper.applicationContext())
    }

    fun <T> request(request: Request<T>): Cache.Entry? {
        request.setShouldCache(true)
        requestQueue.add(request)
        return request.cacheEntry
    }

    // ImageLoader

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object: ImageLoader.ImageCache {

            private val cache = LruCache<String, Bitmap>(42)

            override fun getBitmap(url: String): Bitmap {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }

        })
    }

}
