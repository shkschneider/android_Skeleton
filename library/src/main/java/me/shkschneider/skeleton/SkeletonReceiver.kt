package me.shkschneider.skeleton

import android.os.Handler

import java.io.Serializable

import me.shkschneider.skeleton.helper.HandlerHelper

// android.support.v4.os.ResultReceiver
abstract class SkeletonReceiver {

    private var mHandler: Handler? = null

    init {
        mHandler = HandlerHelper.main()
    }

    fun post(id: String, serializable: Serializable) {
        mHandler!!.post(MyRunnable(id, serializable))
    }

    protected abstract fun onReceive(id: String, serializable: Serializable?)

    private inner class MyRunnable(internal val mId: String, internal val mSerializable: Serializable) : Runnable {

        override fun run() {
            onReceive(mId, mSerializable)
        }

    }

}
