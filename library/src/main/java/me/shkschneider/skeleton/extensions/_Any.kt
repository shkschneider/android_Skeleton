package me.shkschneider.skeleton.extensions

import kotlin.reflect.KClass

// Tag

inline val Any.TAG
    get() = this.javaClass.simpleName.orEmpty()

// Klass (uses no reflection)

fun <T:Any> KClass<T>.simpleName(): String {
    return this.java.simpleName.orEmpty()
}

fun <T:Any> KClass<T>.qualifiedName(): String {
    return this.java.name.orEmpty()
}

fun <T:Any> KClass<T>.packageName(): String {
    return this.java.name.substringBeforeLast(".")
}

// Avoids nulls to be translated to "null" (ex: CharSequence)
// On String, use .orEmpty().toString()
// This extension work on Any?
fun Any?.toStringOrEmpty(): String {
    return this?.toString().orEmpty()
}

// Ternary
// <https://github.com/vpaliyX/android-extensions>

@Deprecated("Respect the language choices.")
infix fun <T> Boolean.then(value: T?)
        = if (this) value else null

@Deprecated("Respect the language choices.")
inline fun <T> Boolean.then(function: () -> T, default: () -> T)
        = if (this) function() else default()

@Deprecated("Respect the language choices.")
inline infix fun <reified T> Boolean.then(function: () -> T)
        = if (this) function() else null

@Deprecated("Respect the language choices.")
inline infix fun <reified T, reified Type> Type?.then(callback: (Type) -> T)
        = if (this != null) callback(this) else null
