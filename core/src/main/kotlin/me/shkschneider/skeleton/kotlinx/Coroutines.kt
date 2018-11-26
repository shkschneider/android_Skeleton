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

    // runs job
    fun <T: Any> launch(work: suspend (() -> T?)): Job {
        return launch(Dispatchers.Default, work, null)
    }

    // runs job on dispatcher
    fun <T: Any> launch(context: CoroutineContext, work: suspend (() -> T?)): Job {
        return launch(context, work, null)
    }

    // runs job then executes callback with the result
    fun <T: Any> launch(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job {
        return launch(Dispatchers.Default, work, callback)
    }

    // runs job on dispatcher then executes callback with the result
    fun <T: Any> launch(context: CoroutineContext, work: suspend (() -> T?), callback: ((T?) -> Unit)? = null): Job {
        return CoroutineScope(context).launch {
            val data = CoroutineScope(context).async {
                return@async work()
            }.await()
            callback?.let {
                it(data)
            }
        }
    }

    suspend fun <T: Any> async(context: CoroutineContext, work: (() -> T?)) =
            CoroutineScope(context).async rt@ { return@rt work() }.await()

}