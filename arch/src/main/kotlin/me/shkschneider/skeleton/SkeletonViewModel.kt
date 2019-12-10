package me.shkschneider.skeleton

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import java.util.UUID

@Deprecated("This is for reference only")
class SkeletonViewModel : ViewModel() {

    /**
     * Lazy-loading of a MutableLiveData with initialization.
     * I think a lazy would always be nice.
     */

    val data by lazy {
        MutableLiveData<Any>().apply {
            postValue(false)
        }
    }

    /**
     * Exposing LiveData from a private initialized MutableLiveData.
     * Exposes a member as LiveData instead of a MutableLiveData.
     */

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    /**
     * Exposing LiveData from a private initialized MutableLiveData
     * that triggers when MutableLiveData's value was null or when specified.
     */

    private val users = MutableLiveData<List<UUID>>()

    private fun loadUsers() {
        // ...
    }

    fun getUsers(force: Boolean = false): LiveData<List<UUID>> {
        if (users.value == null || force) {
            loadUsers()
        }
        return users
    }

    /**
     * Exposing a method for a private LiveData
     * that triggers when MutableLiveData initializes.
     * (This was the Google's example way.)
     */

    private lateinit var projects: MutableLiveData<List<UUID>>

    fun getProjects(): LiveData<List<UUID>> {
        if (!::projects.isInitialized) {
            projects = MutableLiveData()
            // loadUsers()
        }
        return projects
    }

    /**
     * Using SkeletonLiveData.
     */

    val id = SkeletonLiveData<UUID>()

    fun loadId(lifecycleOwner: LifecycleOwner) {
        id += UUID.randomUUID()
        id.observe(lifecycleOwner, Observer {
            TODO()
        })
    }

}
