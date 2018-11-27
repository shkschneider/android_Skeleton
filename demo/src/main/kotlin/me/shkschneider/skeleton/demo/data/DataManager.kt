package me.shkschneider.skeleton.demo.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import me.shkschneider.skeleton.kotlinx.Coroutines

object DataManager {

    @WorkerThread
    suspend fun loadModels(): List<MyModel>? = Coroutines.async(Dispatchers.IO) {
        val db = MyDatabase.get()
        db.clearAllTables()
        db.myModels().insert(MyModel(userName = "user.name1"))
        db.myModels().insert(MyModel(userName = "user.name2"))
        return@async getModels()
    }

    @WorkerThread
    fun getModels(): List<MyModel> =
            MyDatabase.get().myModels().getAll()

}
