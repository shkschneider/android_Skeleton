package me.shkschneider.skeleton.android.arch

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import me.shkschneider.skeleton.android.core.SkeletonFragment

fun SkeletonFragment.getViewModelProvider(): ViewModelProvider =
    activity?.let { ViewModelProvider(it) } ?: ViewModelProvider(this)

inline fun <reified T : ViewModel> SkeletonFragment.getViewModel(): T =
    getViewModelProvider().get(T::class.java)

inline fun <reified T : ViewModel> SkeletonFragment.viewModel() = lazy {
    getViewModelProvider().get(T::class.java)
}

// <https://stackoverflow.com/a/37727576/603270>
fun SkeletonFragment.fragmentManager(): FragmentManager? {
    return if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) childFragmentManager else parentFragmentManager
}
