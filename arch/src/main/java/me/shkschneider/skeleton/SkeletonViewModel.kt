package me.shkschneider.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

@Deprecated("This is for reference only")
class SkeletonViewModel : ViewModel() {

    /**
     * Lazy-loading of a MutableLiveData with initialization.
     */

    val data by lazy {
        MutableLiveData<Any>().apply {
            postValue(false)
        }
    }

    /**
     * Exposing LiveData from an initialized MutableLiveData.
     */

    private val _loading = MutableLiveData<Boolean>()
    fun loading(): LiveData<Boolean> = _loading

    /**
     * Exposing a method for a LiveData
     * that triggers when MutableLiveData initializes.
     */

    private lateinit var users: MutableLiveData<List<UUID>>

    fun getUsers(): LiveData<List<UUID>> {
        if (!::users.isInitialized) {
            users = MutableLiveData()
            // loadUsers()
        }
        return users
    }

}