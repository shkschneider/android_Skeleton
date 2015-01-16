package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

// <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
public class MemoryCacher<K, V> {

    private LruCache<K, V> mCache;

    public MemoryCacher() {
        // One eighth of the application memory is allocated for our cache.
        // On a normal/hdpi device this is a minimum of around 4MB (32/8).
        // A full screen GridView filled with images on a device with
        // 800x480 resolution would use around 1.5MB (800*480*4 bytes),
        // so this would cache a minimum of around 2.5 pages of images in memory.
        final int size = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
        mCache = new LruCache<>(size);
    }

    synchronized public boolean put(@NonNull final K key, final V value) {
        return (! (mCache.put(key, value) == null));
    }

    synchronized public V get(@NonNull final K key) {
        return mCache.get(key);
    }

    synchronized public void clear() {
        mCache.evictAll();
    }

    public int size() {
        return mCache.size();
    }

}
