package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.ui.MyRecyclerView;
import me.shkschneider.skeleton.ui.MyRecyclerViewAdapter;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

public class RecyclerViewFragment extends SkeletonFragment implements SwipeRefreshLayout.OnRefreshListener {

    private MyAdapter mAdapter;

    public RecyclerViewFragment() {
        title("RecyclerView");
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adapter

        mAdapter = new MyAdapter(new ArrayList<Locale>());

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
        return inflater.inflate(R.layout.fragment_reyclerview, container, false);
    }

    // Bind

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MyRecyclerView myRecyclerView = (MyRecyclerView) view.findViewById(R.id.myRecyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        myRecyclerView.setLayoutManager(linearLayoutManager);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setAdapter(mAdapter);
        MySwipeRefreshLayout.recyclerViewCompat(skeletonActivity().mySwipeRefreshLayout(), myRecyclerView, linearLayoutManager);
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
        mAdapter.clear(true);
        skeletonActivity().loading(+1);
        RunnableHelper.delay(new Runnable() {
            @Override
            public void run() {
                RunnableHelper.runOnUiThread(skeletonActivity(), new Runnable() {
                    @Override
                    public void run() {
                        for (final Locale locale : Locale.getAvailableLocales()) {
                            if (mAdapter.getItems().contains(locale)) continue;
                            final String country = StringHelper.withoutAccents(locale.getDisplayCountry().trim());
                            if (StringHelper.nullOrEmpty(country)) continue;
                            if (StringHelper.nullOrEmpty(q) || country.toLowerCase().contains(q.toLowerCase())) {
                                mAdapter.add(locale, true);
                                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                            }
                        }
                        skeletonActivity().loading(-1);
                    }
                });
            }
        }, 1, TimeUnit.SECONDS);
    }

    private class MyAdapter extends MyRecyclerViewAdapter<Locale, MyAdapter.MyViewHolder> {

        public MyAdapter(@Nullable final ArrayList<Locale> locales) {
            super(locales);
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sk_listview_item1, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyAdapter.MyViewHolder holder, final int position) {
            final Locale locale = getItem(position);
            holder.text1.setText(StringHelper.withoutAccents(locale.getDisplayCountry().trim()));
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView text1;

            public MyViewHolder(final View itemView) {
                super(itemView);

                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }

        }

    }

}
