package me.shkschneider.skeleton.android.arch

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

private typealias Predicate<T> = (T?) -> Boolean
private typealias Observer<T> = (t: T) -> Unit

fun <T> LiveData<T>.observeIf(owner: LifecycleOwner, predicate: Predicate<T>, observer: Observer<T>) {
    observe(owner, {
        if (predicate(it)) {
            observer(it)
        }
    })
}

fun <T> LiveData<T>.observeUnless(owner: LifecycleOwner, predicate: Predicate<T>, observer: Observer<T>) {
    observe(owner, {
        if (!predicate(it)) {
            observer(it)
        }
    })
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: Observer<T>) =
    observeIf(owner, { it != null }, observer)
