package me.shkschneider.skeleton.datax

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

interface SkeletonDao<T> {

    // @Query("SELECT * FROM ?")
    fun getAll(): List<T> = throw NotImplementedError()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<T>)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: T)

    @Transaction
    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(data: List<T>)

    @Transaction
    @Delete
    fun delete(data: T)

    @Transaction
    @Delete
    fun delete(data: List<T>)

    // @Query("DELETE FROM ?")
    fun deleteAll(): Nothing = throw IllegalStateException("RoomDatabase.clearAllTables()")

}
