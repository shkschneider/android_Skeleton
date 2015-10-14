package me.shkschneider.skeleton;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import me.shkschneider.skeleton.helper.AndroidHelper;

public class SkeletonPreferenceFragment extends PreferenceFragmentCompat {

    // @Nullable
    public SkeletonActivity getSkeletonActivity() {
        return (SkeletonActivity) getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            final ListView listView = (ListView) view.findViewById(android.R.id.list);
            if (listView != null) {
                if ((AndroidHelper.api() < AndroidHelper.API_14 || AndroidHelper.api() > AndroidHelper.API_18)) {
                    listView.setPadding(0, 0, 0, 0);
                }
                listView.setDivider(new ColorDrawable(0x00000000));
            }
        }
        return view;
    }

}
