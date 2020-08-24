package me.shkschneider.skeleton.kotlin.data

import me.shkschneider.skeleton.kotlin.datax.Serializer
import java.io.File
import java.io.Serializable

class Cache(private val dir: File) {

    fun path(): String =
        dir.absolutePath

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
        dir.takeIf { it.exists() }
            ?.list { file, _ ->
                file.delete()
            }
    }

    fun size(): Int =
        dir.takeIf { it.exists() }
            ?.let { dir.list().size } ?: 0 // FIXME unsafe

}
