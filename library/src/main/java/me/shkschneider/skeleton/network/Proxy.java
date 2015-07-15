package me.shkschneider.skeleton.network;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import me.shkschneider.skeleton.helper.ApplicationHelper;

// <https://developer.android.com/training/volley/index.html>
// <https://github.com/mcxiaoke/android-volley>
public class Proxy {

    private static Proxy INSTANCE;

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

    @RequiresPermission(Manifest.permission.INTERNET)
    public static Proxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Proxy();
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ApplicationHelper.context());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(@NonNull final Request<T> request) {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
