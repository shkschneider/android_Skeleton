package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

// <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
public class MemoryCacher<K, V> {

    private LruCache<K, V> mCache;

    public MemoryCacher() {
        // One 8th of the available memory is safe
        // and should be enough for 2.5 pages of images in a GridView (800x480x4)
        // <http://stackoverflow.com/a/15763477>
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
