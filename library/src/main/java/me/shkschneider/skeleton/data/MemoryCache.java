package me.shkschneider.skeleton.data;

import android.support.annotation.IntRange;
import android.support.v4.util.LruCache;

public final class MemoryCache<K, V> extends LruCache<K, V> {

    public MemoryCache() {
        this(42);
    }

    public MemoryCache(@IntRange(from=0) final int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(final K key, final V value) {
        return 1;
    }

    // put()

    // get()

    synchronized public void clear() {
        evictAll();
    }

}
