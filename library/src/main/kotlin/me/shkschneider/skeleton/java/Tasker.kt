package me.shkschneider.skeleton.java

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

import me.shkschneider.skeleton.SkeletonReceiver

// <http://stackoverflow.com/a/9458785>
class Tasker {

    private val _executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var _future: Future<*>? = null

    fun done(): Boolean? {
        return _future?.isDone
    }

    fun cancelled(): Boolean? {
        return _future?.isCancelled
    }

    fun run(task: Task) {
        _future = _executorService.submit(task)
    }

    fun cancel(mayInterruptIfRunning: Boolean): Boolean? {
        return _future?.cancel(mayInterruptIfRunning)
    }

    // <http://stackoverflow.com/a/826283>
    class Task : Runnable {

        private val _runnable: Runnable
        private val _skeletonReceiver: SkeletonReceiver?

        constructor(runnable: Runnable, skeletonReceiver: SkeletonReceiver? = null) {
            this._runnable = runnable
            this._skeletonReceiver = skeletonReceiver
        }

        override fun run() {
            _runnable.run()
            _skeletonReceiver?.post(ClassHelper.simpleName(Tasker::class.java), null)
        }

    }

}
