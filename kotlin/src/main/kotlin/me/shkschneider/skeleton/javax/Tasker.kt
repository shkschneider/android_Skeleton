package me.shkschneider.skeleton.javax

import me.shkschneider.skeleton.kotlinx.Listener
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

// <http://stackoverflow.com/a/9458785>
open class Tasker(
        private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
) {

    private var future: Future<*>? = null

    val done: Boolean?
        get() = future?.isDone

    val cancelled: Boolean?
        get() = future?.isCancelled

    fun run(task: Task) {
        future = executorService.submit(task)
    }

    fun cancel(mayInterruptIfRunning: Boolean = true): Boolean? {
        return future?.cancel(mayInterruptIfRunning)
    }

    // <http://stackoverflow.com/a/826283>
    class Task (
            private val runnable: Runnable,
            private val listener: Listener? = null
    ) : Runnable {

        override fun run() {
            runnable.run()
            listener?.invoke()
        }

    }

}
