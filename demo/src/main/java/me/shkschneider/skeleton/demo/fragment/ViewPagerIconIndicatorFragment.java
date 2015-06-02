package me.shkschneider.skeleton.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class ViewPagerIconIndicatorFragment extends SkeletonFragment {

    private static final int TABS = 3;

    public ViewPagerIconIndicatorFragment() {
        title("ViewPagerIconIndicator");
    }

    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final int layout = (ActivityHelper.portrait() ? R.layout.fragment_viewpagericonindicator : R.layout.fragment_viewpagerlineindicator);
        return inflater.inflate(layout, container, false);
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
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_tab)));
            tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_tab)));
            tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_tab)));
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
            return null;
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

        private ArrayAdapter<String> mAdapter;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            title(title());
            final LayoutInflater layoutInflater = LayoutInflater.from(ApplicationHelper.context());
            mAdapter = new ArrayAdapter<String>(ApplicationHelper.context(), R.layout.sk_listview_item1) {
                @Override
                public View getView(final int position, View convertView, final ViewGroup parent) {
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.sk_listview_item1, parent, false);
                    }
                    ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
                    return convertView;
                }

                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                public boolean isEnabled(final int position) {
                    return false;
                }
            };
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_listview, container, false);
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((ListView) view.findViewById(R.id.listView)).setAdapter(mAdapter);
        }

        @Override
        public String title() {
            return String.format("Page #%d", getArguments().getInt(POSITION)).toUpperCase();
        }

        @Override
        public void onResume() {
            super.onResume();

            final int position = getArguments().getInt(POSITION);
            final int n = 16;
            mAdapter.clear();
            for (int i = 0; i < n; i++) {
                mAdapter.add(String.format("%s/%s: %s", position, (i + 1), StringHelper.random(n)));
            }
            mAdapter.notifyDataSetChanged();
        }

    }

}
