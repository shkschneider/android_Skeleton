package me.shkschneider.skeleton.java

import android.support.annotation.Size

object ListHelper {

    fun <T> contains(objects: List<T>, obj: T): Boolean {
        return objects.contains(obj)
    }

    fun <T> first(@Size(min = 1) objects: List<T>): T {
        return objects[0]
    }

    fun <T> last(@Size(min = 1) objects: List<T>): T {
        return objects[objects.size - 1]
    }

}
