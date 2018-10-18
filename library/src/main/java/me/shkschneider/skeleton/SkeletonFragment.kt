package me.shkschneider.skeleton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import me.shkschneider.skeleton.ui.Inflater

/**
 * https://developer.android.com/guide/components/fragments.html#Lifecycle
 * https://developer.android.com/reference/android/support/v4/app/Fragment.html
 *
 * onAttach()
 * onCreate()
 * onCreateView()
 * onViewCreated()
 * onActivityCreated()
 * onViewStateRestored()
 * onStart()
 * onResume()
 * onCreateOptionsMenu()
 * onPrepareOptionsMenu()
 * onPause()
 * onSaveInstanceState()
 * onStop()
 * onDestroyView()
 * onDestroy()
 * onDetach()
 */
abstract class SkeletonFragment : Fragment() {

    private var alive = false

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
    }

    fun getViewModelProviders(): ViewModelProvider {
        return activity?.let(ViewModelProviders::of) ?: ViewModelProviders.of(this)
    }

    inline fun <reified T : ViewModel> getViewModel(): T {
        return getViewModelProviders().get(T::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu();
    }

//    fun fragmentManager(): FragmentManager? {
//        val childFragmentManager: FragmentManager? = childFragmentManager
//        return childFragmentManager ?: fragmentManager
//    }

    fun onCreateView(inflater: LayoutInflater, @LayoutRes resId: Int, container: ViewGroup?): View? {
        with(Inflater.inflate(inflater, container, resId)) {
            // HACK: <http://stackoverflow.com/a/18274767>
            setBackgroundResource(R.color.sk_android_background)
            isClickable = true
            isFocusable = true
            return this
        }
    }

    // Loading

    fun loading(): Int? {
        if (!alive()) return null
        return (activity as? SkeletonActivity)?.loading()
    }

    fun loading(i: Int) {
        if (!alive()) return
        (activity as? SkeletonActivity)?.loading(i)
    }

    fun loading(b: Boolean) {
        if (!alive()) return
        (activity as? SkeletonActivity)?.loading(b)
    }

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onCreateView(inflater, R.layout.sk_fragment, container)
    }

    override fun onStart() {
        super.onStart()
        alive = true
    }

    override fun onStop() {
        alive = false
        super.onStop()
    }

    fun alive(): Boolean {
        return alive
    }

    // <https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/to.html>
    companion object {

        inline fun <reified T : SkeletonFragment> newInstance(target: Class<T>) =
                target.newInstance()

        inline fun <reified T : SkeletonFragment> newInstance(target: Class<T>, vararg arguments: Pair<String, Any>) =
                target.newInstance().apply {
                    setArguments(bundleOf(*arguments))
                }

    }

}
