package me.shkschneider.skeleton.java

import me.shkschneider.skeleton.SkeletonReceiver
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

// <http://stackoverflow.com/a/9458785>
class Tasker {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var future: Future<*>? = null

    fun done(): Boolean? {
        return future?.isDone
    }

    fun cancelled(): Boolean? {
        return future?.isCancelled
    }

    fun run(task: Task) {
        future = executorService.submit(task)
    }

    fun cancel(mayInterruptIfRunning: Boolean): Boolean? {
        return future?.cancel(mayInterruptIfRunning)
    }

    // <http://stackoverflow.com/a/826283>
    class Task : Runnable {

        private val runnable: Runnable
        private val skeletonReceiver: SkeletonReceiver?

        constructor(runnable: Runnable, skeletonReceiver: SkeletonReceiver? = null) {
            this.runnable = runnable
            this.skeletonReceiver = skeletonReceiver
        }

        override fun run() {
            runnable.run()
            skeletonReceiver?.post(Tasker::class.java.simpleName, null)
        }

    }

}