package me.shkschneider.skeleton;

import android.support.v4.preference.PreferenceFragment;

/**
 * Base PreferenceFragment you should use!
 * This is the new way to present Settings in Android: using a (Preference)Fragment.
 *
 * - SkeletonActivity skeletonActivity()
 *
 * @see android.support.v4.preference.PreferenceFragment
 */
public class SkeletonPreferenceFragment extends PreferenceFragment {

    public SkeletonActivity skeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

}
