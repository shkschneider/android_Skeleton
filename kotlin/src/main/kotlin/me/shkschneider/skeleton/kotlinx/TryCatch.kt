package me.shkschneider.skeleton.kotlinx

fun <T : Any> tryOr(block: (() -> T?), or: T?): T? =
    try {
        block()
    } catch (e: Exception) {
        or
    }

fun <T : Any> tryOrRun(block: (() -> T?), or: (() -> T?)): T? =
    try {
        block()
    } catch (e: Exception) {
        or()
    }

fun <T : Any> tryOrNull(block: (() -> T?)): T? =
    tryOr<T>(block, null)
