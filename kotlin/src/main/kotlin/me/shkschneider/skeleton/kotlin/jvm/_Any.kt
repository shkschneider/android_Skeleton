package me.shkschneider.skeleton.kotlin.jvm

import kotlin.reflect.KClass

// Tag

inline val Any.TAG
    get() = this.javaClass.simpleName.orEmpty()

// when

val <T> T.exhaustive: T
    get() = this

// Klass (uses no reflection)

fun <T: Any> KClass<T>.simpleName(): String =
        // this.simpleName uses reflection
        this.java.simpleName.orEmpty()

fun <T: Any> KClass<T>.qualifiedName(): String =
        // this.qualifiedName uses reflection
        this.java.name.orEmpty()

fun <T: Any> KClass<T>.packageName(): String =
        this.java.name.substringBeforeLast(".")

// Avoids nulls to be printed as "null" (ex: CharSequence? = null)
fun Any?.toStringOrEmpty(): String =
        this?.toString().orEmpty()
