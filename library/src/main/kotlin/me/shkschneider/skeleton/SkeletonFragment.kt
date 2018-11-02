package me.shkschneider.skeleton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import me.shkschneider.skeleton.uix.Inflater
import kotlin.reflect.KClass

/**
 * https://developer.android.com/guide/components/fragments.html#Lifecycle
 * https://developer.android.com/reference/android/support/v4/app/Fragment.html
 *
 * +---------+----------+-------+------------+
 * | Created | Inflated | Alive | Foreground |
 * +---------+----------+-------+------------+
 * |         |          |       |            | onAttach()
 * |       x |          |       |            | onCreate()
 * |       x |          |       |            | onCreateView()
 * |       x |        x |       |            | onViewCreated()
 * |       x |        x |       |            | onActivityCreated()
 * |       x |        x |     x |            | onStart()
 * |       x |        x |     x |          x | onResume()
 * +---------+----------+-------+------------+
 * |       x |        x |     x |          x | onPause()
 * |       x |        x |     x |            | onStop()
 * |       x |        x |       |            | onDestroyView()
 * |       x |          |       |            | onDestroy()
 * |         |          |       |            | onDetach()
 * +---------+----------+-------+------------+
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

    // <https://stackoverflow.com/a/37727576/603270>
    fun fragmentManager(): FragmentManager? {
        return if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) fragmentManager else childFragmentManager
    }

    fun onCreateView(inflater: LayoutInflater, @LayoutRes resId: Int, container: ViewGroup?): View? {
        Inflater.inflate(inflater, container, resId).run {
            // HACK: <http://stackoverflow.com/a/18274767>
            setBackgroundResource(R.color.sk_android_background)
            isClickable = true
            isFocusable = true
            return this
        }
    }

    // Loading

    fun loading(): Int {
        if (!alive()) return 0
        return (activity as? SkeletonActivity)?.loading() ?: 0
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

    companion object {

        inline fun <reified T : SkeletonFragment> newInstance(target: KClass<T>) =
                target.java.newInstance()

        inline fun <reified T : SkeletonFragment> newInstance(target: KClass<T>, arguments: Bundle) =
                target.java.newInstance().apply {
                    setArguments(arguments)
                }

        @Deprecated("Use KClass")
        inline fun <reified T : SkeletonFragment> newInstance(target: Class<T>) =
                target.newInstance()

        @Deprecated("Use KClass")
        inline fun <reified T : SkeletonFragment> newInstance(target: Class<T>, arguments: Bundle) =
                target.newInstance().apply {
                    setArguments(arguments)
                }

    }

}
