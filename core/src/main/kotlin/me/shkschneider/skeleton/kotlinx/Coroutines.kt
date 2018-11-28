package me.shkschneider.skeleton.kotlinx

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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

    fun io(work: suspend (() -> Unit)): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            work()
        }
    }

    fun <T: Any> ioThenMain(work: suspend(() -> T?), callback: ((T?) -> Unit)) : Job {
        return CoroutineScope(Dispatchers.Main).launch {
            callback(CoroutineScope(Dispatchers.IO).async {
                return@async work()
            }.await())
        }
    }

    suspend fun <T: Any> async(context: CoroutineContext, work: (() -> T?)): T? =
            CoroutineScope(context).async coroutine@ { return@coroutine work() }.await()

}
