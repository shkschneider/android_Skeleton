package me.shkschneider.app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.ui.viewpager.ViewPagerCircleIndicator;
import me.shkschneider.skeleton.ui.viewpager.ViewPagerIndicatorAdapter;

public class ViewPagerCircleIndicatorFragment extends SkeletonFragment {

    private static final int TABS = 3;

    public ViewPagerCircleIndicatorFragment() {
        title("ViewPagerCircleIndicator");
    }

    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpagercircleindicator, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        // viewPager.setOffscreenPageLimit()
        viewPager.setAdapter(mPagerAdapter);

        final ViewPagerCircleIndicator viewPagerCircleIndicator = (ViewPagerCircleIndicator) view.findViewById(R.id.viewpagercircleindicator);
        if (viewPagerCircleIndicator != null) {
            viewPagerCircleIndicator.setViewPager(viewPager);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mPagerAdapter.notifyDataSetChanged();
    }

    private class MyViewPagerAdapter extends ViewPagerIndicatorAdapter {

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
        public Drawable getPageIcon(int position) {
            return getResources().getDrawable(R.drawable.ic_launcher);
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
            final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
            mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_item1) {
                @Override
                public View getView(final int position, View convertView, final ViewGroup parent) {
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item1, parent, false);
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
