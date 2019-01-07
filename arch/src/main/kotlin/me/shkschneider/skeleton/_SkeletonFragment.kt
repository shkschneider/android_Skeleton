package me.shkschneider.skeleton

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun SkeletonFragment.getViewModelProviders(): ViewModelProvider {
    return activity?.let(ViewModelProviders::of) ?: ViewModelProviders.of(this)
}

inline fun <reified T: ViewModel> SkeletonFragment.getViewModel(): T {
    return getViewModelProviders().get(T::class.java)
}

inline fun <reified T: ViewModel> SkeletonFragment.viewModel() = lazy {
    getViewModelProviders().get(T::class.java)
}

// <https://stackoverflow.com/a/37727576/603270>
fun SkeletonFragment.fragmentManager(): FragmentManager? {
    return if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) fragmentManager else childFragmentManager
}
