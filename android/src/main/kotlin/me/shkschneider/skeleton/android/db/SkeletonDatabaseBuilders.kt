package me.shkschneider.skeleton.android.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import me.shkschneider.skeleton.android.core.helper.ContextHelper
import me.shkschneider.skeleton.kotlin.simpleName
import kotlin.reflect.KClass

object SkeletonDatabaseBuilders {

    fun <T: RoomDatabase> memory(klass: KClass<T>,
                                 migrations: List<Migration>?= null,
                                 callback: RoomDatabase.Callback? = null): RoomDatabase.Builder<T> {
        return Room.inMemoryDatabaseBuilder(ContextHelper.applicationContext(), klass.java).apply {
            migrations?.forEach {
                addMigrations(it)
            }
            callback?.let {
                addCallback(callback)
            }
        }
    }

    fun <T: RoomDatabase> disk(klass: KClass<T>,
                               migrations: List<Migration>?= null,
                               callback: RoomDatabase.Callback? = null): RoomDatabase.Builder<T> {
        return Room.databaseBuilder(ContextHelper.applicationContext(), klass.java, "${klass.simpleName()}.db").apply {
            migrations?.forEach {
                addMigrations(it)
            }
            callback?.let {
                addCallback(callback)
            }
        }
    }

}
