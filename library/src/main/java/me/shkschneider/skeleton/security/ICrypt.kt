package me.shkschneider.skeleton.security

abstract class ICrypt<T>(key: T) {

    abstract fun algorithm(): String

    abstract fun key(): T

    abstract fun encrypt(src: T): T?

    abstract fun decrypt(src: T): T?

}