package me.shkschneider.app;

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

import me.shkschneider.skeleton.ActivityHelper;
import me.shkschneider.skeleton.RunnableHelper;
import me.shkschneider.skeleton.StringHelper;
import me.shkschneider.skeleton.MyListView;
import me.shkschneider.skeleton.SkeletonFragment;

public class ListViewFragment extends SkeletonFragment {

    private ArrayAdapter<String> mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listview, container, false);

        final MyListView myListView = (MyListView) view.findViewById(R.id.mylistview);
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), android.R.layout.simple_list_item_1);
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
                refresh();
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
//        Ion.with(this)
//                .load("http://ifconfig.me/ip")
//                .asString()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<String>>() {
//                    @Override
//                    public void onCompleted(final Exception e, final Response<String> result) {
//                        loading(false);
//
//                        final int responseCode = result.getHeaders().getResponseCode();
//                        final String responseMessage = result.getHeaders().getResponseMessage();
//                        final String response = result.getResult();
//                    }
//                });
    }

}
