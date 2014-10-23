package me.shkschneider.skeleton;

import android.support.v4.app.Fragment;

import me.shkschneider.skeleton.helper.ClassHelper;

/**
 * title()
 * title(String)
 * skeletonActivity()
 */
public class SkeletonFragment extends Fragment {

    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.name(SkeletonFragment.class));
    }

    public String title() {
        return mTitle;
    }

    protected void title(final String title) {
        mTitle = title;
    }

    public SkeletonActivity skeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

}
