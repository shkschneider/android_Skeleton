package me.shkschneider.skeleton;

import android.support.v4.preference.PreferenceFragment;

public class SkeletonPreferenceFragment extends PreferenceFragment {

    // @Nullable
    public SkeletonActivity getSkeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

}
