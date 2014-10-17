package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;
import me.shkschneider.skeleton.helper.StringHelper;
import me.shkschneider.skeleton.ui.MyListView;
import me.shkschneider.skeleton.SkeletonFragment;

public class ListViewFragment extends SkeletonFragment {

    private ArrayAdapter<String> mAdapter;

    public ListViewFragment() {
        title("ListView");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listview, container, false);

        final MyListView myListView = (MyListView) view.findViewById(R.id.mylistview);
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_item1);
        myListView.setAdapter(mAdapter);
        myListView.setCallback(new MyListView.Callback() {
            @Override
            public void overscroll(final int n) {
                if (!skeletonActivity().loading()) {
                    skeletonActivity().charging(n);
                }
            }

            @Override
            public void overscroll() {
                ActivityHelper.toast("overscroll");
            }

            @Override
            public void bottom() {
                ActivityHelper.toast("bottom");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    public void refresh() {
        skeletonActivity().loading(true);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                skeletonActivity().loading(false);
                final Locale[] locales = Locale.getAvailableLocales();
                final List<String> countries = new ArrayList<String>();
                for (final Locale locale : locales) {
                    final String country = locale.getDisplayCountry().trim();
                    if (!StringHelper.nullOrEmpty(country) && !countries.contains(country)) {
                        countries.add(country);
                    }
                }
                Collections.shuffle(countries);
                mAdapter.clear();
                mAdapter.addAll(countries);
                mAdapter.notifyDataSetChanged();
            }
        };
        RunnableHelper.delay(runnable, 1, TimeUnit.SECONDS);
    }

}
