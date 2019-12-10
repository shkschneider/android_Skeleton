package me.shkschneider.skeleton

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk

internal fun mockLifecycleOwner(): LifecycleOwner {
    val owner = mockk<LifecycleOwner>(relaxed = true)
    val lifecycle = LifecycleRegistry(owner)
    lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    every { owner.lifecycle } returns lifecycle
    return owner
}

fun <T> SkeletonLiveData<T>.mockObserve(): Observer<T?> {
    val observer = mockk<Observer<T?>>(relaxed = true)
    this.observe(mockLifecycleOwner(), observer)
    return observer
}
