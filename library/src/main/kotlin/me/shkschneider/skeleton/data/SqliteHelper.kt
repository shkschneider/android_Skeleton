package me.shkschneider.skeleton.data

import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase

// <http://satyan.github.io/sugar/>
object SqliteHelper {

    fun rows(sqLiteDatabase: SQLiteDatabase, table: String): Int {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, table).toInt()
    }

    // TODO ...

}
