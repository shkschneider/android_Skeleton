package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class ListViewFragment extends SkeletonFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayAdapter<String> mAdapter;

    public ListViewFragment() {
        title("ListView");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adapter

        final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
        mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.sk_listview_item1) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.sk_listview_item1, parent, false);
                }
                final String item = getItem(position);
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(item);
                return convertView;
            }
        };

        // Search

        setHasOptionsMenu(true);
        skeletonActivity().searchable(getResources().getString(R.string.dots), new SkeletonActivity.SearchCallback() {
            @Override
            public void onSearchTextChange(final String q) {
                refresh(q);
            }

            @Override
            public void onSearchTextSubmit(final String q) {
                // Ignore
            }
        });
    }

    // Inflate

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                ActivityHelper.toast(mAdapter.getItem(position));
            }
        });
        skeletonActivity().swipeRefreshLayoutCompat(listView);
    }

    // Load

    @Override
    public void onResume() {
        super.onResume();

        skeletonActivity().refreshable(true, this);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        refresh(null);
    }

    public void refresh(final String q) {
        skeletonActivity().loading(+1);
        new Thread() {
            @Override
            public void run() {
                final Locale[] locales = Locale.getAvailableLocales();
                final List<String> countries = new ArrayList<String>();
                for (final Locale locale : locales) {
                    final String country = StringHelper.withoutAccents(locale.getDisplayCountry().trim());
                    if (!StringHelper.nullOrEmpty(country)
                            && (StringHelper.nullOrEmpty(q) || country.toLowerCase().contains(q.toLowerCase()))
                            && !countries.contains(country)) {
                        countries.add(country);
                    }
                }
                Collections.sort(countries, new Comparator<String>() {
                    @Override
                    public int compare(final String s1, final String s2) {
                        return s1.compareTo(s2);
                    }
                });
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mAdapter.addAll(countries);
                        mAdapter.notifyDataSetChanged();
                        skeletonActivity().loading(-1);
                    }
                });
            }
        }.start();
    }

}
