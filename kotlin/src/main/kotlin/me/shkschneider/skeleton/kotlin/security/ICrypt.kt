package me.shkschneider.skeleton.kotlin.security

abstract class ICrypt<T>(@Suppress("UNUSED_PARAMETER") key: T) {

    abstract fun algorithm(): String

    abstract fun key(): T

    abstract fun encrypt(src: T): T?

    abstract fun decrypt(src: T): T?

}