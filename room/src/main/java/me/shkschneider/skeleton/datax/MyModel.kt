package me.shkschneider.skeleton.datax

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import java.io.Serializable

@Entity(tableName = "MyModels", foreignKeys = [], indices = [])
data class MyModel(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val userName: String,
        val firstName: String,
        val lastName: String,
        val email: String
) : Serializable {

    constructor(userName: String, firstName: String, lastName: String, email: String) :
            this(null, userName, firstName, lastName, email)

    @Dao
    abstract class MyModelDao : SkeletonDao<MyModel> {

        @Query("SELECT * FROM MyModels")
        abstract override fun getAll(): List<MyModel>

    }

}
