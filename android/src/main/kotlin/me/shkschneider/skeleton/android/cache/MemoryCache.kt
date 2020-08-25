package me.shkschneider.skeleton.android.cache

import androidx.annotation.IntRange
import androidx.collection.LruCache

open class MemoryCache<K, V>(
        @IntRange(from = 0) maxSize: Int = 42
) : LruCache<K, V>(maxSize) {

    override fun sizeOf(key: K, value: V): Int =
        1

    // put()

    // get()

    @Synchronized
    fun clear() {
        evictAll()
    }

}
