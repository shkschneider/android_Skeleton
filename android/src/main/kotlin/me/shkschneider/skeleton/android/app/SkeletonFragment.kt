package me.shkschneider.skeleton.android.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.view.Inflater
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
 * |       x |        x |     x |            | onViewStateRestored()
 * |       x |        x |     x |          x | onResume()
 * +---------+----------+-------+------------+
 * |       x |        x |     x |          x | onPause()
 * |       x |        x |     x |            | onStop()
 * |       x |        x |       |            | onSaveInstanceState()
 * |       x |        x |       |            | onDestroyView()
 * |       x |          |       |            | onDestroy()
 * |         |          |       |            | onDetach()
 * +---------+----------+-------+------------+
 */
abstract class SkeletonFragment : Fragment() {

    var alive = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu();
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

    fun loading(): Int = when {
        !alive -> 0
        else -> (activity as? SkeletonActivity)?.loading() ?: 0
    }

    fun loading(i: Int) {
        if (!alive) return
        (activity as? SkeletonActivity)?.loading(i)
    }

    fun loading(b: Boolean) {
        if (!alive) return
        (activity as? SkeletonActivity)?.loading(b)
    }

    // Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        onCreateView(inflater, R.layout.sk_fragment, container)

    override fun onStart() {
        super.onStart()
        alive = true
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // restore
    }

    override fun onStop() {
        alive = false
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save
    }

    companion object {

        inline fun <reified T : SkeletonFragment> newInstance(target: KClass<T>): T =
                target.java.newInstance()

        inline fun <reified T : SkeletonFragment> newInstance(target: KClass<T>, arguments: Bundle): T =
                target.java.newInstance().apply {
                    setArguments(arguments)
                }

    }

}
