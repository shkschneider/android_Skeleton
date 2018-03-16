package me.shkschneider.skeleton.data

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.LoaderManager

// <http://satyan.github.io/sugar/>
object DatabaseHelper {

    fun loader(loaderManager: LoaderManager, id: Int, args: Bundle, callbacks: LoaderManager.LoaderCallbacks<Any>) {
        loaderManager.getLoader<Any>(id)?.let {
            loaderManager.restartLoader(id, args, callbacks)
        } ?: run {
            loaderManager.initLoader(id, args, callbacks)
        }
    }

    fun rows(sqLiteDatabase: SQLiteDatabase, table: String): Int {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, table).toInt()
    }

}
