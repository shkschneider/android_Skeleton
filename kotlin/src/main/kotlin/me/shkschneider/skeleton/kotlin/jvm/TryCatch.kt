package me.shkschneider.skeleton.kotlin.jvm

import me.shkschneider.skeleton.kotlin.log.Logger

fun <T : Any> tryOrRun(block: (() -> T?), or: ((Exception) -> T?), finally: (() -> Unit)? = null): T? =
    try {
        block()
    } catch (e: Exception) {
        Logger.wtf(e)
        or(e)
    } finally {
        finally?.invoke()
    }
fun <T : Any> tryOrRun(block: (() -> T?), or: ((Exception) -> T?)): T? =
    tryOrRun(block, or)

fun <T : Any> tryOr(or: T?, block: (() -> T?), finally: (() -> Unit)? = null): T? =
    tryOrRun(block, { or }, finally)
fun <T : Any> tryOr(or: T?, block: (() -> T?)): T? =
    tryOrRun(block, { or }, null)

fun <T : Any> tryOrNull(block: (() -> T?), finally: (() -> Unit)? = null): T? =
    tryOr(null, block, finally)
fun <T : Any> tryOrNull(block: (() -> T?)): T? =
    tryOr(null, block)
