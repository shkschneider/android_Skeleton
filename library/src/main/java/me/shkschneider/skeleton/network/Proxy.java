package me.shkschneider.skeleton.network;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import me.shkschneider.skeleton.helper.ApplicationHelper;

// <https://developer.android.com/training/volley/index.html>
public class Proxy {

    private static class ProxyHolder {

        private static Proxy INSTANCE = new Proxy();

    }

    public static Proxy getInstance() {
        return ProxyHolder.INSTANCE;
    }

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private Proxy() {
        mRequestQueue = getRequestQueue();
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
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ApplicationHelper.context());
        }
        return mRequestQueue;
    }

    // getRequestQueue().add()
    // getRequestQueue().getCache().clear()

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
