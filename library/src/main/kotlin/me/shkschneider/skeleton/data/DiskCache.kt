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
    open class Cache {

        private val _dir: File

        constructor(dir: File) {
            _dir = dir
        }

        @Synchronized
        fun put(key: String, value: Serializable): Boolean {
            if (_dir.exists()) {
                val path = FileHelper.join(_dir.absolutePath, key)
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
            if (_dir.exists()) {
                val path = FileHelper.join(_dir.absolutePath, key)
                val file = FileHelper.get(path)
                if (file.exists()) {
                    return SerializeHelper.read(file) as Serializable?
                }
            }
            return null
        }

        @Synchronized
        fun clear() {
            if (_dir.exists()) {
                _dir.list().map { FileHelper.join(_dir.absolutePath, it) }.forEach { FileHelper.get(it).delete() }
            }
        }

        fun size(): Int {
            val dir = ExternalDataHelper.cache()
            return if (dir!!.exists()) {
                dir.list().size
            } else 0
        }

    }

}