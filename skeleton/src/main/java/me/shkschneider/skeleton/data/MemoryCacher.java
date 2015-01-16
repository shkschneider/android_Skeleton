package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import me.shkschneider.skeleton.helper.LogHelper;

public class MemoryCacher {

    private LruCache<String, Object> mCache;

    private static int recommendedSize() {
        // One 8th of the available memory is safe
        // and should be enough for 2.5 pages of images in a GridView (800x480x4)
        // <http://stackoverflow.com/a/15763477>
        return (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
    }

    public MemoryCacher() {
        this(recommendedSize());
    }

    public MemoryCacher(int size) {
        final int maxSize = recommendedSize();
        if (size > maxSize) {
            LogHelper.verbose("Size seemed too big, maximum recommended is: " + maxSize);
            size = maxSize;
        }
        mCache = new LruCache<>(size);
    }

    synchronized public boolean put(@NonNull final String key, final Object value) {
        return (! (mCache.put(key, value) == null));
    }

    synchronized public Object get(@NonNull final String key) {
        return mCache.get(key);
    }

    synchronized public void clear() {
        mCache.evictAll();
    }

    public int size() {
        return mCache.size();
    }

}
