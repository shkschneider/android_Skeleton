package me.shkschneider.skeleton.android.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import me.shkschneider.skeleton.android.core.SkeletonActivity

fun SkeletonActivity.getViewModelProviders(): ViewModelProvider {
    return ViewModelProviders.of(this)
}

inline fun <reified T : ViewModel> SkeletonActivity.getViewModel(): T {
    return getViewModelProviders().get(T::class.java)
}

inline fun <reified T : ViewModel> SkeletonActivity.viewModel() = lazy {
    getViewModelProviders().get(T::class.java)
}
