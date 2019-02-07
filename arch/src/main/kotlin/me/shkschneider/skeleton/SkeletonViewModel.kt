package me.shkschneider.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

@Deprecated("This is for reference only")
class SkeletonViewModel : ViewModel() {

    /**
     * Lazy-loading of a MutableLiveData with initialization.
     *
     * I think a lazy would always be nice.
     */

    val data by lazy {
        MutableLiveData<Any>().apply {
            postValue(false)
        }
    }

    /**
     * Exposing LiveData from a private initialized MutableLiveData.
     *
     * Always try to expose a LiveData instead of a MutableLiveData.
     */

    private val _loading = MutableLiveData<Boolean>()

    fun loading(): LiveData<Boolean>  = _loading

    /**
     * Exposing LiveData from a private initialized MutableLiveData
     * that triggers when MutableLiveData's value was null.
     *
     * This decides when to load.
     */

    private val users = MutableLiveData<List<UUID>>()

    private fun loadUsers() {
        // ...
    }

    fun getUsers(): LiveData<List<UUID>> {
        if (users.value == null) {
            loadUsers()
        }
        return users
    }

    /**
     * Same this as previously,
     * but the loading behavior can be overriden.
     *
     * This decides when to load, with manuel override available.
     */

    private val users2 = MutableLiveData<List<UUID>>()

    private fun loadUsers2() {
        // ...
    }

    fun getUsers2(force: Boolean = false): LiveData<List<UUID>> {
        if (users2.value == null || force) {
            loadUsers()
        }
        return users2
    }

    /**
     * Exposing a method for a private LiveData
     * that triggers when MutableLiveData initializes.
     *
     * This was the Google's example way.
     */

    private lateinit var projects: MutableLiveData<List<UUID>>


    fun getProjects(): LiveData<List<UUID>> {
        if (!::projects.isInitialized) {
            projects = MutableLiveData()
            // loadUsers()
        }
        return projects
    }

}
