package me.shkschneider.skeleton;

import android.support.v4.app.Fragment;

import me.shkschneider.skeleton.helper.ClassHelper;

public class SkeletonFragment extends Fragment {

    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.name(SkeletonFragment.class));
    }

    protected void title(final String title) {
        mTitle = title;
    }

    public String title() {
        return mTitle;
    }

    public SkeletonActivity skeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

}
