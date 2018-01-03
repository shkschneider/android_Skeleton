package me.shkschneider.skeleton.java

import java.util.Arrays
import java.util.HashMap

object MapHelper {

    fun <K, V> item(key: K, value: V): Map<K, V> {
        val item = HashMap<K, V>()
        item.put(key, value)
        return item
    }

    inline fun <reified K, V> keys(objects: Map<K, V>): List<K> {
        return Arrays.asList(*objects.keys.toTypedArray())
    }

    inline fun <K, reified V> values(objects: Map<K, V>): List<V> {
        return Arrays.asList(*objects.values.toTypedArray())
    }

    inline fun <reified K, V> containsKey(objects: Map<K, V>, key: K): Boolean {
        return keys(objects).contains(key)
    }

    inline fun <K, reified V> containsValue(objects: Map<K, V>, value: V): Boolean {
        return values(objects).contains(value)
    }

    inline fun <reified K, V> size(objects: Map<K, V>): Int {
        return keys(objects).size
    }

}
