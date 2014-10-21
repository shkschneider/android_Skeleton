package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.shkschneider.app.R;
import me.shkschneider.app.controller.ListViewIndexer;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.StringHelper;

public class ListViewFragment extends SkeletonFragment {

    private ListViewIndexer<String> mAdapter;

    public ListViewFragment() {
        title("ListView");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final View view = inflater.inflate(R.layout.fragment_listview, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new ListViewIndexer<String>(skeletonActivity(), R.layout.listview_item1) {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }
        };
        listView.setAdapter(mAdapter);
        listView.setFastScrollEnabled(true);
        listView.setFastScrollAlwaysVisible(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                ActivityHelper.toast(mAdapter.getItem(position));
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

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

    @Override
    public void onResume() {
        super.onResume();

        refresh(null);
    }

    public void refresh(final String q) {
//        final Locale[] locales = Locale.getAvailableLocales();
//        final List<String> countries = new ArrayList<String>();
//        for (final Locale locale : locales) {
//            final String country = locale.getDisplayCountry().trim();
//            if (! StringHelper.nullOrEmpty(country)
//                    && (StringHelper.nullOrEmpty(q) || country.toLowerCase().contains(q.toLowerCase()))
//                    && ! countries.contains(country)) {
//                countries.add(country);
//            }
//        }
//        Collections.sort(countries, new Comparator<String>() {
//            @Override
//            public int compare(final String s1, final String s2) {
//                return s1.compareTo(s2);
//            }
//        });
        mAdapter.clear();
//        mAdapter.addAll(countries);
        mAdapter.addAll("Annona", "Apples", "Apricots", "Avocado",
                "Banano", "Bilberry", "Blackberry",
                "Cantalope", "Coconut", "Currant", "Cherry", "Cherimoya",
                "Date", "Damson", "Durian", "Elderberry",
                "Fig", "Feijoa",
                "Grapefruit", "Grape", "Gooseberry", "Guava",
                "Honeydew melon", "Huckleberry",
                "Jackfruit", "Juniper Berry", "Jambul", "jujube",
                "kiwi", "Kumquat",
                "Lemons", "Limes", "Lychee",
                "Mango", "Mandarin", "Mangostine", "Nectaraine",
                "Orange", "Olive",
                "Prunes", "Pears", "Plum", "Pineapple", "Peach", "Papaya", "Passionfruit", "Pomegranate", "Pomelo",
                "Raspberries", "Rock melon", "Rambutan",
                "Strawberry", "Sweety", "Salmonberry", "Satsuma",
                "Tangerines", "Tomato",
                "Ugli",
                "Watermelon", "Woodapple");
        mAdapter.notifyDataSetChanged();
    }

}
