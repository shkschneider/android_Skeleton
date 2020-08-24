package me.shkschneider.skeleton.android.core.data

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.loader.app.LoaderManager

// <http://satyan.github.io/sugar/>
@UiThread
object DatabaseHelper {

    object Loader {

        @MainThread
        fun start(loaderManager: LoaderManager, id: Int, args: Bundle? = null, callbacks: LoaderManager.LoaderCallbacks<Any>) {
            loaderManager.getLoader<Any>(id)?.let {
                loaderManager.restartLoader(id, args, callbacks) // starts or restarts
            } ?: run {
                loaderManager.initLoader(id, args, callbacks)
            }
        }

        @MainThread
        fun stop(loaderManager: LoaderManager, id: Int) {
            loaderManager.destroyLoader(id)
        }

    }

    // DatabaseUtils.queryIsEmpty() is @hide
    fun empty(db: SQLiteDatabase, table: String): Boolean =
        rows(db, table) == 0

    fun rows(db: SQLiteDatabase, table: String, selection: String? = null, selectionArgs: Array<String>? = null): Int =
        DatabaseUtils.queryNumEntries(db, table, selection, selectionArgs).toInt()

}
