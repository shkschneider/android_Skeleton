package me.shkschneider.skeleton.datax

import me.shkschneider.skeleton.data.DataHelper
import me.shkschneider.skeleton.data.FileHelper
import java.io.File
import java.io.Serializable

// <http://developer.android.com/reference/java/io/Serializable.html>
object DiskCache {

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesInternal>
    open class Internal : Cache(DataHelper.Internal.cache())

    // <http://developer.android.com/guide/topics/data/data-storage.html#filesExternal>
    open class External : Cache(DataHelper.External.cache()
            ?: DataHelper.Internal.cache())

    abstract class Cache(private val dir: File) {

        fun path(): String {
            return dir.absolutePath
        }

        @Synchronized
        fun put(key: String, value: Serializable): Boolean {
            if (dir.exists()) {
                val path = FileHelper.join(dir.absolutePath, key)
                val file = FileHelper.get(path)
                if (file.exists()) {
                    file.delete()
                }
                return Serializer.write(value, file)
            }
            return false
        }

        @Synchronized
        fun get(key: String): Serializable? {
            if (dir.exists()) {
                val path = FileHelper.join(dir.absolutePath, key)
                val file = FileHelper.get(path)
                if (file.exists()) {
                    return Serializer.read(file) as Serializable?
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
            val dir = DataHelper.External.cache()
            return dir?.exists()?.let { dir.list().size } ?: 0
        }

    }

}
