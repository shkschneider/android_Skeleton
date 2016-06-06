package me.shkschneider.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * https://developer.android.com/guide/components/fragments.html#Lifecycle
 * https://developer.android.com/reference/android/support/v4/app/Fragment.html
 *
 *     onAttach()
 *     onCreate()
 *     onCreateView()
 *     onViewCreated()
 *     onActivityCreated()
 *     onViewStateRestored()
 *     onStart()
 *     onResume()
 *     onPause()
 *     onStop()
 *     onSaveInstanceState()
 *     onDestroyView()
 *     onDestroy()
 *     onDetach()
 */
public abstract class SkeletonFragment extends Fragment {

    protected SkeletonActivity mActivity;

    public SkeletonFragment() {
        mActivity = (SkeletonActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        if (! (context instanceof SkeletonActivity)) {
            throw new RuntimeException("Activity was not SkeletonActivity");
        }
        mActivity = (SkeletonActivity) context;
    }

    @Nullable
    public View onCreateView(final LayoutInflater inflater, @LayoutRes final int resid, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(resid, container, false);
        // HACK: <http://stackoverflow.com/a/18274767>
        view.setBackgroundResource(R.color.sk_android_background);
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return onCreateView(inflater, R.layout.sk_fragment, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.bindMySwipeRefreshLayout();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public boolean alive() {
        return isVisible();
    }

    // @Nullable
    public SkeletonActivity skeletonActivity() {
        if (mActivity == null) {
            mActivity = (SkeletonActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
