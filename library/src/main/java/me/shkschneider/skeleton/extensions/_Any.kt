package me.shkschneider.skeleton.extensions

import kotlin.reflect.KClass

// Uses no reflection
fun <T:Any> KClass<T>.simpleName(): String {
    return this.java.simpleName.orEmpty()
}
// Uses no reflection
fun <T:Any> KClass<T>.qualifiedName(): String {
    return this.java.name.orEmpty()
}
// Uses no reflection
fun <T:Any> KClass<T>.packageName(): String {
    return this.java.name?.substringBeforeLast(".").orEmpty()
}

// Avoids nulls to be translated to "null" (ex: CharSequence)
// On String, use .orEmpty().toString()
// This extension work on Any?
fun Any?.toStringOrEmpty(): String {
    return this?.toString().orEmpty()
}
