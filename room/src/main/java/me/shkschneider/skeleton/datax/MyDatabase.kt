package me.shkschneider.skeleton.datax

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    MyModel::class
], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myModelDao(): MyModel.MyModelDao

    companion object {

        fun get() = db

        private val db: MyDatabase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SkeletonDatabaseBuilders.memory(MyDatabase::class, arrayListOf(
                    // Migrations...
            )).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }

    }

}
