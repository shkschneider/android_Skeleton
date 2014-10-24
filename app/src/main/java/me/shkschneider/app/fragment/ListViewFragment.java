package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.IndexableAdapter;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.StringHelper;

public class ListViewFragment extends SkeletonFragment {

    private IndexableAdapter<String> mAdapter;

    public ListViewFragment() {
        title("ListView");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
        mAdapter = new IndexableAdapter<String>(skeletonActivity(), R.layout.listview_item1) {
            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (view == null) {
                    view = layoutInflater.inflate(R.layout.listview_item1, parent, false);
                    ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position));
                }
                return view;
            }
        };

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
        refresh(null);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listview, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        mAdapter.withSections(listView);
        listView.setAdapter(mAdapter);
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                ActivityHelper.toast(mAdapter.getItem(position));
            }
        });
        return view;
    }

    public void refresh(final String q) {
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
        mAdapter.clear();
        mAdapter.addAll(countries);
        mAdapter.notifyDataSetChanged();
    }

}
