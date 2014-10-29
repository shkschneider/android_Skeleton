package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.adapter.IndexedRecyclerAdapter;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.StringHelper;

public class IndexedRecyclerFragment extends SkeletonFragment {

    private IndexedRecyclerAdapter mAdapter;

    public IndexedRecyclerFragment() {
        title("IndexedRecycler");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new IndexedRecyclerAdapter();

        setHasOptionsMenu(true);
        skeletonActivity().searchable(getResources().getString(R.string.dots), new SkeletonActivity.SearchCallback() {
            @Override
            public void onSearchTextChange(final String q) {
                // Ignore
            }

            @Override
            public void onSearchTextSubmit(final String q) {
                refresh(q);
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(skeletonActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh(null);
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
