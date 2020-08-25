package me.shkschneider.skeleton.kotlin.jvm

import java.io.Serializable

/**
 * Substitute for kotlin.Result
 * https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/result.md#limitations
 */
class Result<out T> internal constructor(
    internal val value: Any?
) : Serializable {

    val isSuccess: Boolean get() = value !is Failure
    val isFailure: Boolean get() = value is Failure

    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): T? = when (value) {
        is Failure -> null
        else -> value as T
    }

    fun exceptionOrNull(): Throwable? = when (value) {
        is Failure -> value.exception
        else -> null
    }

    fun fold(onSuccess: (value: T) -> Unit, onFailure: (exception: Throwable) -> Unit) {
        getOrNull()?.let {
            onSuccess(it)
        } ?: exceptionOrNull()?.let {
            onFailure(it)
        }
    }

    override fun toString(): String = when (value) {
        is Failure -> "Failure(${value.exception})"
        else -> "Success($value)"
    }

    companion object {

        fun <T> success(value: T): Result<T> = Result(value)

        fun <T> failure(exception: Throwable): Result<T> = Result(Failure(exception))

    }

    internal class Failure(
        @JvmField
        val exception: Throwable
    ) : Serializable {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }

}
