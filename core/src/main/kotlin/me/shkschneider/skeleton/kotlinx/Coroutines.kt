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

    fun <T: Any> io(work: suspend (() -> T?)): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            work()
        }
    }

    fun <T: Any> ioThenMain(work: suspend(() -> T?), callback: ((T?) -> Unit)) : Job {
        return CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async {
                return@async work()
            }.await()
            callback(data)
        }
    }

    suspend fun <T: Any> async(context: CoroutineContext, work: (() -> T?)) =
            CoroutineScope(context).async rt@ { return@rt work() }.await()

}
