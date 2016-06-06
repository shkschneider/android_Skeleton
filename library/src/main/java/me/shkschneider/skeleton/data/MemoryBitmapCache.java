package me.shkschneider.skeleton.data;

import android.graphics.Bitmap;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

// <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
public final class MemoryBitmapCache extends LruCache<String, Bitmap> {

    @SuppressWarnings("deprecation")
    public MemoryBitmapCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        // Use 1/8th of the available memory for this memory cache.
        super((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    @Deprecated // Avoid
    public MemoryBitmapCache(@IntRange(from=0) final int size) {
        super(size);
    }

    @Override
    protected int sizeOf(@NonNull final String key, @NonNull final Bitmap bitmap) {
        // The cache size will be measured in kilobytes rather than
        // number of items.
        return (bitmap.getByteCount() / 1024);
    }

    // put()

    // get()

    synchronized public void clear() {
        evictAll();
    }

}
