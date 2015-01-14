package me.shkschneider.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import me.shkschneider.skeleton.java.ClassHelper;

public class SkeletonFragment extends Fragment {

    protected SkeletonActivity mActivity;
    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.name(SkeletonFragment.class));
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mActivity = (SkeletonActivity) activity;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // <http://stackoverflow.com/a/8395263>
        if (view != null) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
                final View v = ((ViewGroup) view).getChildAt(i);
                if (v instanceof AbsListView) {
                    skeletonActivity().swipeRefreshLayoutListViewCompat((AbsListView) v);
                }
            }
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
            return (SkeletonActivity) getActivity();
        }
        return mActivity;
    }

    public void onRefresh() {
        // Implement
    }

}
