package me.shkschneider.skeleton

import me.shkschneider.skeleton.helper.HandlerHelper
import me.shkschneider.skeleton.helper.RunnableHelper
import java.io.Serializable

// android.support.v4.os.ResultReceiver
abstract class SkeletonReceiver {

    fun post(id: String, serializable: Serializable?) {
        RunnableHelper.post(HandlerHelper.main(), SkeletonRunnable(id, serializable))
    }

    protected abstract fun onReceive(id: String, serializable: Serializable?)

    private inner class SkeletonRunnable(
            private val id: String,
            private val serializable: Serializable?
    ) : Runnable {

        override fun run() {
            onReceive(id, serializable)
        }

    }

}
