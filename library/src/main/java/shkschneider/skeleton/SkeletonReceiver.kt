package me.shkschneider.skeleton

import me.shkschneider.skeleton.helper.HandlerHelper
import java.io.Serializable

// android.support.v4.os.ResultReceiver
abstract class SkeletonReceiver {

    private var handler = HandlerHelper.main()

    fun post(id: String, serializable: Serializable?) {
        handler.post(MyRunnable(id, serializable))
    }

    protected abstract fun onReceive(id: String, serializable: Serializable?)

    private inner class MyRunnable : Runnable {

        private val id: String
        private val serializable: Serializable?

        constructor(id: String, serializable: Serializable?) {
            this.id = id
            this.serializable = serializable
        }

        override fun run() {
            onReceive(id, serializable)
        }

    }

}
