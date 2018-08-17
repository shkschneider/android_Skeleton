package me.shkschneider.skeleton.data

import android.support.annotation.IntRange
import android.support.v4.util.LruCache

class MemoryCache<K, V>(
        @IntRange(from = 0) maxSize: Int = 42
) : LruCache<K, V>(maxSize) {

    override fun sizeOf(key: K, value: V): Int {
        return 1
    }

    // put()

    // get()

    @Synchronized
    fun clear() {
        evictAll()
    }

}
