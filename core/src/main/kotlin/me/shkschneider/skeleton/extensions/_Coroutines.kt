package me.shkschneider.skeleton.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.shkschneider.skeleton.helper.ThreadHelper
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * fun load() {
 *   coroutine {                      // starting a coroutine
 *     val data = getData()           // getting data (blocking because of implicit await)
 *     ui {
 *       Toaster.show(data)           // action on main thread with data
 *     }
 *   }
 * }
 * suspend fun getData(): ? = async { // starts async task
 *   // ...                           // makes work in background
 *   return@async data                // returns data
 * }
 */

fun Any.coroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job {
    return GlobalScope.launch(context, start, block)
}

suspend fun <T> Any.async(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
): T {
    return GlobalScope.async(context, start, block).await()
}

fun CoroutineScope.ui(block: () -> Unit) {
    ThreadHelper.foregroundThread(Runnable { block() })
}