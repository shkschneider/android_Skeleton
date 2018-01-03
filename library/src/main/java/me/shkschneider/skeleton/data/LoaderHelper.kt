package me.shkschneider.skeleton.data

import android.os.Bundle
import android.support.v4.app.LoaderManager

object LoaderHelper {

    fun startOrRestart(loaderManager: LoaderManager, id: Int, args: Bundle, callbacks: LoaderManager.LoaderCallbacks<Any>) {
        if (loaderManager.getLoader<Any>(id) != null) {
            loaderManager.restartLoader(id, args, callbacks)
        } else {
            loaderManager.initLoader(id, args, callbacks)
        }
    }

}
