package me.shkschneider.skeleton.demo.data

import androidx.annotation.WorkerThread

object DataManager {

    @WorkerThread
    fun dummy() {
        with(MyDatabase.get()) {
            clearAllTables()
            myModels().insert(MyModel(userName = "user.name1"))
            myModels().insert(MyModel(userName = "user.name2"))
        }
    }

    @WorkerThread
    fun getModels(): List<MyModel> =
            MyDatabase.get().myModels().getAll()

}
