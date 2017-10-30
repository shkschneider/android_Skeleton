package me.shkschneider.skeleton

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.shkschneider.skeleton.helper.ContextHelper

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

    protected var mActivity: SkeletonActivity? = null

    init {
        mActivity = activity as SkeletonActivity
    }

    val applicationContext: Context get() {
        if (mActivity != null) {
            return mActivity!!.applicationContext
        }
        return if (context != null) {
            context.applicationContext
        } else ContextHelper.applicationContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu();
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context !is SkeletonActivity) {
            throw RuntimeException("Activity was not SkeletonActivity")
        }
        mActivity = context
    }

    fun onCreateView(inflater: LayoutInflater, @LayoutRes resid: Int, container: ViewGroup?): View? {
        val view = inflater.inflate(resid, container, false)
        // HACK: <http://stackoverflow.com/a/18274767>
        view.setBackgroundResource(R.color.sk_android_background)
        return view
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onCreateView(inflater, R.layout.sk_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity!!.bindMySwipeRefreshLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun alive(): Boolean {
        return isVisible
    }

    // @Nullable
    fun skeletonActivity(): SkeletonActivity {
        return mActivity!!
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    // Lifecycle

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

}
