package me.shkschneider.skeleton.kotlin.work

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.shkschneider.skeleton.kotlin.log.Logger

/**
 * +------------+----------+-------------+-------------+
 * | state      | isActive | isCompleted | isCancelled |
 * +------------+----------+-------------+-------------+
 * | new        | false    | false       | false       |
 * | active     | true     | false       | false       |
 * | completing | true     | false       | false       |
 * | cancelling | false    | false       | true        |
 * | cancelled  | false    | true        | true        |
 * | completed  | false    | true        | false       |
 * +------------+----------+-------------+-------------+
 */
object Coroutines {

    fun io(work: suspend (() -> Unit)): Job =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
        CoroutineScope(Dispatchers.Main).launch {
            callback(CoroutineScope(Dispatchers.IO).async {
                return@async work() // TODO try/catch?
            }.await())
        }

}

// fire-and-forget
fun <T : Any> io(block: suspend (() -> T)) =
    CoroutineScope(Dispatchers.IO).safeLaunch({
        block()
    })

fun <T : Any> CoroutineScope.safeLaunch(block: suspend (() -> T), onError: ((Exception) -> Unit)? = null): Job = launch {
    try {
        block()
    } catch (e: Exception) {
        Logger.wtf(e)
        onError?.invoke(e)
    }
}

fun <T : Any> CoroutineScope.safeAsync(block: suspend (() -> T), onError: ((Exception) -> Unit)? = null): Deferred<T?> = async {
    try {
        return@async block()
    } catch (e: Exception) {
        Logger.wtf(e)
        onError?.invoke(e)
        return@async null
    }
}
