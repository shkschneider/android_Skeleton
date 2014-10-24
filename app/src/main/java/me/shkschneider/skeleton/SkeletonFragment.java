package me.shkschneider.skeleton;

import android.support.v4.app.Fragment;

import me.shkschneider.skeleton.helper.ClassHelper;

/**
 * Base Fragment you should use!
 *
 * - boolean alive()
 * - String title()
 * - void title(String)
 * - SkeletonActivity skeletonActivity()
 *
 * @see android.support.v4.app.Fragment
 * @see <http://developer.android.com/guide/components/fragments.html#Creating>
 */
public class SkeletonFragment extends Fragment {

    protected String mTitle;

    public SkeletonFragment() {
        title(ClassHelper.name(SkeletonFragment.class));
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
        return (SkeletonActivity) getActivity();
    }

}
