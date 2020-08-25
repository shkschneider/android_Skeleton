package me.shkschneider.skeleton.android.cache

import android.graphics.Bitmap
import androidx.collection.LruCache

// <http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#memory-cache>
open class MemoryBitmapCache(
        size: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
) : LruCache<String, Bitmap>(size) {

    // The cache size will be measured in kilobytes rather than number of items.
    override fun sizeOf(key: String, bitmap: Bitmap): Int =
        bitmap.byteCount / 1024

    // put()

    // get()

    @Synchronized
    fun clear() {
        evictAll()
    }

}
