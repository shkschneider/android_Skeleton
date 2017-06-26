package me.shkschneider.skeleton.network;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import me.shkschneider.skeleton.helper.ContextHelper;

// <https://developer.android.com/training/volley/requestqueue.html>
public class Proxy {

    private static Proxy PROXY;

    public static synchronized Proxy get() {
        if (PROXY == null) {
            PROXY = new Proxy();
        }
        return PROXY;
    }

    private final RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;

    private Proxy() {
        mRequestQueue = Volley.newRequestQueue(ContextHelper.applicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> CACHE = new LruCache<>(42);

            @Override
            public Bitmap getBitmap(@NonNull final String url) {
                return CACHE.get(url);
            }

            @Override
            public void putBitmap(@NonNull final String url, @NonNull final Bitmap bitmap) {
                CACHE.put(url, bitmap);
            }

        });
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    // getRequestQueue().add()
    // getRequestQueue().getCache().clear()

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
