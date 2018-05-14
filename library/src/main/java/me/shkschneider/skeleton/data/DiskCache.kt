package me.shkschneider.skeleton.data

import java.io.File
import java.io.Serializable

// <http://developer.android.com/reference/java/io/Serializable.html>
object DiskCache {

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesInternal>
    class Internal(dir: File) : Cache(dir)

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesExternal>
    class External(dir: File) : Cache(dir)

    // Prevents direct initialization
    sealed class Cache(private val dir: File) {

        @Synchronized
        fun put(key: String, value: Serializable): Boolean {
            if (dir.exists()) {
                val path = FileHelper.join(dir.absolutePath, key)
                val file = FileHelper.get(path)
                if (file.exists()) {
                    file.delete()
                }
                return SerializeHelper.write(value, file)
            }
            return false
        }

        @Synchronized
        fun get(key: String): Serializable? {
            if (dir.exists()) {
                val path = FileHelper.join(dir.absolutePath, key)
                val file = FileHelper.get(path)
                if (file.exists()) {
                    return SerializeHelper.read(file) as Serializable?
                }
            }
            return null
        }

        @Synchronized
        fun clear() {
            if (dir.exists()) {
                dir.list().map { FileHelper.join(dir.absolutePath, it) }
                        .forEach { FileHelper.get(it).delete() }
            }
        }

        fun size(): Int {
            val dir = ExternalDataHelper.cache()
            return dir?.exists()?.let { dir.list().size } ?: 0
        }

    }

}
