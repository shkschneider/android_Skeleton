package me.shkschneider.skeleton;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Base PreferenceFragment you should use!
 * This is the new way to present Settings in Android: using a (Preference)Fragment.
 * This class also removes paddings of the ListView (that is not new-API-friendly).
 *
 * - SkeletonActivity skeletonActivity()
 *
 * @see android.support.v4.preference.PreferenceFragment
 */
public class SkeletonPreferenceFragment extends PreferenceFragment {

    public SkeletonActivity skeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            final ListView listView = (ListView) view.findViewById(android.R.id.list);
            listView.setPadding(0, 0, 0, 0);
        }
        return view;
    }

}
