package me.shkschneider.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class SkeletonViewModel : ViewModel() {

    /**
     * Option 1: exposing LiveData from an initialized MutableLiveData
     */

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    /**
     * Option 2: exposing a method for a LiveData
     *           that triggers when MutableLiveData initializes
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