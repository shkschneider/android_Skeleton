package me.shkschneider.skeleton.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeIf(owner: LifecycleOwner, predicate: (T?) -> Boolean, observer: (t: T) -> Unit) {
    observe(owner, Observer {
        if (predicate(it)) {
            it?.let(observer)
        }
    })
}

fun <T> LiveData<T>.observeUnless(owner: LifecycleOwner, predicate: (T?) -> Boolean, observer: (t: T) -> Unit) {
    observe(owner, Observer {
        if (!predicate(it)) {
            it?.let(observer)
        }
    })
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) =
        observeIf(owner, { it != null }, observer)
