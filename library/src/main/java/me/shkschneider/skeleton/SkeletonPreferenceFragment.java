package me.shkschneider.skeleton;

import android.support.v4.preference.PreferenceFragmentCompat;

public class SkeletonPreferenceFragment extends PreferenceFragmentCompat {

    // @Nullable
    public SkeletonActivity getSkeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

}
