package me.shkschneider.skeleton.demo.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Query
import me.shkschneider.skeleton.datax.SkeletonDao
import java.io.Serializable

@Entity(tableName = "MyModels", foreignKeys = [], indices = [])
data class MyModel(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "user_name") var userName: String,
        @ColumnInfo(name = "first_name") var firstName: String? = null,
        @ColumnInfo(name = "last_name") var lastName: String? = null,
        @ColumnInfo(name = "email") var email: String? = null,
        @Ignore var avatar: Bitmap? = null
) : Serializable {

    constructor(): this(0, "", null, null, null, null)

    @Dao
    abstract class MyModelDao : SkeletonDao<MyModel> {

        @Query("SELECT * FROM MyModels")
        abstract override fun getAll(): List<MyModel>

    }

}
