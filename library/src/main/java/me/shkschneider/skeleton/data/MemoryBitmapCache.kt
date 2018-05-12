package me.shkschneider.skeleton.data

import android.graphics.Bitmap
import android.support.v4.util.LruCache

// <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
class MemoryBitmapCache(
        size: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
) : LruCache<String, Bitmap>(size) {

    override fun sizeOf(key: String, bitmap: Bitmap): Int {
        // The cache size will be measured in kilobytes rather than
        // number of items.
        return bitmap.byteCount / 1024
    }

    // put()

    // get()

    @Synchronized
    fun clear() {
        evictAll()
    }

}
