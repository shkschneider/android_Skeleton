package me.shkschneider.skeleton

import android.os.Handler

import java.io.Serializable

import me.shkschneider.skeleton.helper.HandlerHelper

// android.support.v4.os.ResultReceiver
abstract class SkeletonReceiver {

    private var _handler: Handler = HandlerHelper.main()

    fun post(id: String, serializable: Serializable?) {
        _handler.post(MyRunnable(id, serializable))
    }

    protected abstract fun onReceive(id: String, serializable: Serializable?)

    private inner class MyRunnable : Runnable {

        private val _id: String
        private val _serializable: Serializable?

        constructor(id: String, serializable: Serializable?) {
            _id = id
            _serializable = serializable
        }

        override fun run() {
            onReceive(_id, _serializable)
        }

    }

}
