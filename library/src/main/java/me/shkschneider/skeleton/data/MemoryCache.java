package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

public final class MemoryCache<K, V> {

    public static final int DEFAULT_SIZE = 42;

    private LruCache<K, V> mCache;

    public MemoryCache() {
        this(DEFAULT_SIZE);
    }

    public MemoryCache(final int size) {
        mCache = new LruCache<K, V>(size) {

            @Override
            protected int sizeOf(final K key, final V value) {
                return 1;
            }

        };
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

    // <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
    public static final class Bitmap extends LruCache<String, android.graphics.Bitmap> {

        public Bitmap() {
            // One eighth of the application memory is allocated for our cache.
            // On a normal/hdpi device this is a minimum of around 4MB (32/8).
            // A full screen GridView filled with images on a device with
            // 800x480 resolution would use around 1.5MB (800*480*4 bytes),
            // so this would cache a minimum of around 2.5 pages of images in memory.
            this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
        }

        public Bitmap(final int size) {
            super(size);
        }

        @Override
        protected int sizeOf(final String key, final android.graphics.Bitmap bitmap) {
            return (bitmap.getByteCount() / 1024);
        }

        synchronized public void clear() {
            evictAll();
        }

    }

}
