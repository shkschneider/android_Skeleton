package me.shkschneider.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.java.ClassHelper;

public class SkeletonFragment extends Fragment {

    protected SkeletonActivity mActivity;
    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.simpleName(SkeletonFragment.class));
        mActivity = (SkeletonActivity) getActivity();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mActivity = (SkeletonActivity) activity;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        skeletonActivity().bindMySwipeRefreshLayout();

        // <https://stackoverflow.com/q/30752713/>
        if (view != null) {
            view.postDelayed(new Runnable() {

                @Override
                public void run() {
                    skeletonActivity().transitioning(false);
                }

            }, TimeUnit.SECONDS.toMillis(1));
        }
    }

    public boolean alive() {
        return isVisible();
    }

    public String title() {
        return mTitle;
    }

    protected void title(final String title) {
        mTitle = title;
    }

    public SkeletonActivity skeletonActivity() {
        if (mActivity == null) {
            mActivity = (SkeletonActivity) getActivity();
        }
        return mActivity;
    }

}
