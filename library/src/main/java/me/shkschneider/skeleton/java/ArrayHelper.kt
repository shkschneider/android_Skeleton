package me.shkschneider.skeleton.java

import android.support.annotation.IntRange

import java.util.Arrays

object ArrayHelper {

    @IntRange(from = -1)
    fun <T> index(objects: Array<T>, obj: T): Int {
        return objects.indices.firstOrNull { objects[it] == obj } ?: -1
    }

    fun <T> list(objects: Array<T>): List<T> {
        return Arrays.asList(*objects)
    }

    fun <T> contains(objects: Array<T>, obj: T): Boolean {
        return ListHelper.contains(list(objects), obj)
    }

    fun <T> first(objects: Array<T>): T? {
        return ListHelper.first(list(objects))
    }

    fun <T> last(objects: Array<T>): T? {
        return ListHelper.last(list(objects))
    }

}
