package me.shkschneider.skeleton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

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

    protected val activity: AppCompatActivity by lazy {
        context as AppCompatActivity
    }

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
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
        val view = inflater.inflate(resId, container, false)
        // HACK: <http://stackoverflow.com/a/18274767>
        view.setBackgroundResource(R.color.sk_android_background)
        view.isClickable = true
        view.isFocusable = true
        return view
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun alive(): Boolean {
        return isAdded && isResumed
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

}
