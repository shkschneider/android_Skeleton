package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.java.RandomHelper;

public class ViewPagerTextIndicatorFragment extends SkeletonFragment {

    private static final int TABS = 3;

    public ViewPagerTextIndicatorFragment() {
        title("ViewPagerTextIndicator");
    }

    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpagertextindicator, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(TABS);
        viewPager.setAdapter(mPagerAdapter);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        if (tabLayout != null) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.actionBarColor));
            tabLayout.setTabTextColors(getResources().getColor(R.color.primaryColorDark), getResources().getColor(R.color.actionBarForegroundColor));
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(final TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition(), true);
                }

                @Override
                public void onTabUnselected(final TabLayout.Tab tab) {
                    // Ignore
                }

                @Override
                public void onTabReselected(final TabLayout.Tab tab) {
                    // Ignore
                }

            });
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mPagerAdapter.notifyDataSetChanged();
    }

    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(final int position) {
            return DummyFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return ((DummyFragment) getItem(position)).title();
        }

        @Override
        public int getCount() {
            return TABS;
        }

    }

    public static class DummyFragment extends SkeletonFragment {

        public static DummyFragment newInstance(final int position) {
            final DummyFragment dummyFragment = new DummyFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt(POSITION, position);
            dummyFragment.setArguments(bundle);
            return dummyFragment;
        }

        private static final String POSITION = "position";

        private MyAdapter<String> mAdapter;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            title(title());
            mAdapter = new MyAdapter<>(new ArrayList<String>());
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_reyclerview, container, false);
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((RecyclerView) view.findViewById(R.id.myRecyclerView)).setAdapter(mAdapter);
        }

        @Override
        public String title() {
            return String.format(LocaleHelper.locale(), "Page #%d", getArguments().getInt(POSITION)).toUpperCase();
        }

        @Override
        public void onResume() {
            super.onResume();

            final int position = getArguments().getInt(POSITION);
            final int n = 16;
            mAdapter.mObjects.clear();
            for (int i = 0; i < n; i++) {
                mAdapter.mObjects.add(String.format(LocaleHelper.locale(), "%s/%s: %s", position, (i + 1), RandomHelper.string(n)));
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
            }
        }

    }

    private static class MyAdapter<T> extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<T> mObjects;

        public MyAdapter(@NonNull final List<T> objects) {
            mObjects = objects;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            // Inflated items should have: android:foreground="?android:attr/selectableItemBackground"
            // <http://stackoverflow.com/q/26961147>
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
            final T object = mObjects.get(position);
            holder.text1.setText(object.toString());
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnLongClickListener(null);
        }

        @Override
        public int getItemCount() {
            return mObjects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text1;

            public ViewHolder(final View itemView) {
                super(itemView);

                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }

        }

    }

}
