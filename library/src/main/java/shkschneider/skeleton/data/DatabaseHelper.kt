package me.shkschneider.skeleton.data

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.LoaderManager

// <http://satyan.github.io/sugar/>
@UiThread
object DatabaseHelper {

    object Loader {

        fun start(loaderManager: LoaderManager, id: Int, args: Bundle? = null, callbacks: LoaderManager.LoaderCallbacks<Any>) {
            loaderManager.getLoader<Any>(id)?.let {
                loaderManager.restartLoader(id, args, callbacks) // starts or restarts
            } ?: run {
                loaderManager.initLoader(id, args, callbacks)
            }
        }

        fun stop(loaderManager: LoaderManager, id: Int) {
            loaderManager.destroyLoader(id)
        }

    }

    fun empty(db: SQLiteDatabase, table: String): Boolean {
        // DatabaseUtils.queryIsEmpty(db, table)
        return rows(db, table) == 0
    }

    fun rows(db: SQLiteDatabase, table: String, selection: String? = null, selectionArgs: Array<String>? = null): Int {
        return DatabaseUtils.queryNumEntries(db, table, selection, selectionArgs).toInt()
    }

}
