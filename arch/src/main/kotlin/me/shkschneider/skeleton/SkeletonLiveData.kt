package me.shkschneider.skeleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SkeletonLiveData<T>(
        initialValue: T? = null,
        private val fallback: T? = null,
        private val distinct: Boolean = false,
        private val filter: ((T?) -> Boolean)? = null
) : LiveData<T?>() {

    private val data = MutableLiveData<T?>().apply {
        initialValue?.let { super.postValue(it) }
    }
    private var old: T? = initialValue

    override protected fun postValue(value: T?) = with(value ?: fallback) {
        when {
            filter != null && !filter.invoke(this) -> return
            distinct && old == this -> return
            else -> super.postValue(this).also { if (distinct) old = this }
        }
    }

    operator fun plusAssign(other: T?) = postValue(other)

    suspend operator fun plusAssign(other: (suspend () -> T?)) = postValue(other())

    object Filters {

        val NonNull: ((Any?) -> Boolean) = { t: Any? -> t != null }

    }

}
