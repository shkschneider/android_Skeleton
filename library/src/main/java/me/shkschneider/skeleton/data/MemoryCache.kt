package me.shkschneider.skeleton.data

import androidx.annotation.IntRange
import androidx.collection.LruCache

open class MemoryCache<K, V>(
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
