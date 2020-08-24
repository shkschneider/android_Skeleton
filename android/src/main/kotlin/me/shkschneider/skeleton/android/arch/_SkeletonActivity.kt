package me.shkschneider.skeleton.android.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.shkschneider.skeleton.android.core.SkeletonActivity

fun SkeletonActivity.getViewModelProviders(): ViewModelProvider =
    ViewModelProvider(this)

inline fun <reified T : ViewModel> SkeletonActivity.getViewModel(): T =
    getViewModelProviders().get(T::class.java)

inline fun <reified T : ViewModel> SkeletonActivity.viewModel() = lazy {
    getViewModelProviders().get(T::class.java)
}
